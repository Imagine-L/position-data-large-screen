package top.liubaiblog.poh.common.constant;

/**
 * @author 留白
 * @description 连接iot平台相关常量
 */
public class IotClientConstants {
    // 定位交换机
    public final static String LOCATION_EXCHANGE = "location_exchange";
    // 用户交换机
    public final static String USER_EXCHANGE = "user_exchange";
    // 实时定位队列名称
    public final static String REAL_TIME_POSITION_QUEUE = "real_time_position";
    // 绑定交换机和队列的路由key
    public final static String REAL_TIME_QUEUE_BINDING_EXCHANGE = "real_time_queue_binding_exchange";
    // 间隔定位消息分发队列
    public final static String INTERVAL_LOCATION_RELEASE_QUEUE = "interval_location_release";
    // 消息存活时间(毫秒)
    public final static int MESSAGE_EXPIRATION_MILLIS = 200;
    // 用户队列的前缀
    public final static String USER_QUEUE_PREFIX = "user:";
}
