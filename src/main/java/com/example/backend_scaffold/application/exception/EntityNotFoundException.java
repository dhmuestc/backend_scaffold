package com.example.backend_scaffold.application.exception;

/**
 * 实体未找到异常类
 * <p>
 * 用于表示请求的实体资源不存在的异常
 * </p>
 *
 * @author example
 */
public class EntityNotFoundException extends BusinessException {

    /**
     * 构造函数
     *
     * @param entityName 实体名称
     * @param id         实体ID
     */
    public EntityNotFoundException(String entityName, Object id) {
        super(404, String.format("%s with id %s not found", entityName, id));
    }

    /**
     * 构造函数
     *
     * @param message 错误消息
     */
    public EntityNotFoundException(String message) {
        super(404, message);
    }
}