package top.liubaiblog.poh.common.exception;

/**
 * @author 留白
 * @description 注册信息异常
 */
public class RegisterMessageException extends RuntimeException {
    public RegisterMessageException(String explanation) {
        super(explanation);
    }

    public RegisterMessageException() {
    }
}
