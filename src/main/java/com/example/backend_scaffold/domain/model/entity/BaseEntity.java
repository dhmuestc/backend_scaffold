package com.example.backend_scaffold.domain.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 * <p>
 * 所有实体类的父类，包含通用的审计字段
 * </p>
 *
 * @author example
 */
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 版本号，用于乐观锁
     */
    private Long version = 0L;

    /**
     * 是否删除，用于逻辑删除
     */
    private Boolean isDeleted = false;
}