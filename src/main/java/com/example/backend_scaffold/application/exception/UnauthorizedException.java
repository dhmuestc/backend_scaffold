package com.example.backend_scaffold.application.exception;

/**
 * 未授权异常类
 * <p>
 * 用于表示用户未授权访问资源的异常
 * </p>
 *
 * @author example
 */
public class UnauthorizedException extends BusinessException {

    /**
     * 构造函数
     */
    public UnauthorizedException() {
        super(401, "Unauthorized access");
    }

    /**
     * 构造函数
     *
     * @param message 错误消息
     */
    public UnauthorizedException(String message) {
        super(401, message);
    }

    /**
     * 构造函数
     *
     * @param message 错误消息
     * @param cause   异常原因
     */
    public UnauthorizedException(String message, Throwable cause) {
        super(401, message, cause);
    }
}