package top.liubaiblog.poh.common.exception;

/**
 * @author 留白
 * @description 用户删除自己
 */
public class RemoveOwnException extends RuntimeException {

    public RemoveOwnException() {
    }

    public RemoveOwnException(String message) {
        super(message);
    }
}
