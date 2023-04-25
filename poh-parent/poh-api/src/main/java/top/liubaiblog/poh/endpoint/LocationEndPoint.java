package top.liubaiblog.poh.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.liubaiblog.baselutils.spring.util.SpringAppUtils;
import top.liubaiblog.baselutils.spring.util.redis.RedisUtils;
import top.liubaiblog.poh.common.constant.SecureConstants;
import top.liubaiblog.poh.common.message.EquipLocationMessage;
import top.liubaiblog.poh.pojo.dto.LoginUserDTO;
import top.liubaiblog.poh.pojo.vo.EquipLocationVO;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 留白
 * @description
 */
@Slf4j
@Component
@ServerEndpoint(value = "/equipment/location")
public class LocationEndPoint {

    public final static Map<String, List<LocationEndPoint>> endPointMap = new ConcurrentHashMap<>();

    private Session session;

    private Long queryUid;

    private static volatile RedisUtils redisUtils;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 建立连接后回调
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        log.info("建立websocket连接, sessionId -> {}", session.getId());
        setRedisUtils();
    }

    /**
     * 接收到客户端消息后回调
     * 客户端建立连接后应立即发送token
     */
    @OnMessage
    public void onMessage(String token, Session session) {
        // 获取客户端信息
        log.info("endpoint接收到客户端消息 -> {}", token);
        this.session = session;
        LoginUserDTO loginUser = redisUtils.valGetObject(SecureConstants.REDIS_LOGIN_USER_TOKEN_PREFIX + token, LoginUserDTO.class);
        Long queryUid = loginUser.getQueryUid();
        this.queryUid = queryUid;
        log.info("endpoint判断客户端的queryId -> {}", queryUid);
        List<LocationEndPoint> locationEndPoints = endPointMap.get(queryUid + "");
        // 添加客户端
        if (locationEndPoints == null) {
            synchronized (endPointMap) {
                locationEndPoints = endPointMap.get(queryUid + "");
                if (locationEndPoints == null) {
                    locationEndPoints = new CopyOnWriteArrayList<>();
                    locationEndPoints.add(this);
                    endPointMap.put(queryUid + "", locationEndPoints);
                }
            }
        } else {
            // 如果该客户端以及存在，则直接返回，不重复添加
            if (locationEndPoints.contains(this)) {
                return;
            }
            locationEndPoints.add(this);
        }
    }

    /**
     * 向客户端发送消息
     */
    public static void sendMessage(Long uid, EquipLocationVO locationVO) {
        List<LocationEndPoint> locationEndPoints = endPointMap.get(uid + "");
        if (locationEndPoints == null) {
            return;
        }
        locationEndPoints.forEach(locationEndPoint -> {
            String msg = null;
            try {
                msg = objectMapper.writeValueAsString(locationVO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            try {
                locationEndPoint.session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 关闭连接后回调
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        List<LocationEndPoint> locationEndPoints = endPointMap.get(String.valueOf(queryUid));
        if (locationEndPoints == null) {
            return;
        }
        locationEndPoints.remove(this);
    }

    /**
     * 连接出现错误后回调
     */
    @OnError
    public void onError(Session session, Throwable e) {
        log.error("========== websocket实时定位模块异常日志[start] ==========");
        log.error("异常用户 -> {}", queryUid);
        log.error("异常信息 -> {}", e.getMessage());
        log.error("========== websocket实时定位模块异常[stop] ==========");
    }

    /**
     * 设置redis模板工具，添加双重检查锁
     */
    private static void setRedisUtils() {
        if (redisUtils == null) {
            synchronized (LocationEndPoint.class) {
                if (redisUtils == null) {
                    redisUtils = (RedisUtils) SpringAppUtils.getBean(RedisUtils.class);
                }
            }
        }
    }
}
