package com.example.backend_scaffold.domain.model.enums;

/**
 * 权限类型枚举
 * <p>
 * 用于定义系统中不同类型的权限
 * </p>
 *
 * @author example
 */
public enum PermissionType {

    /**
     * 菜单权限，用于控制菜单的显示
     */
    MENU,

    /**
     * 按钮权限，用于控制按钮的显示和操作
     */
    BUTTON,

    /**
     * API权限，用于控制API接口的访问
     */
    API,

    /**
     * 数据权限，用于控制数据的访问范围
     */
    DATA
}