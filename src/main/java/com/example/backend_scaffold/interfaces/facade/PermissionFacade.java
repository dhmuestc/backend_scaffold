package com.example.backend_scaffold.interfaces.facade;

import com.example.backend_scaffold.application.dto.permission.PermissionRequest;
import com.example.backend_scaffold.application.dto.permission.PermissionResponse;
import com.example.backend_scaffold.application.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 权限外观类，作为应用服务的门面
 */
@Component
@RequiredArgsConstructor
public class PermissionFacade {

    private final PermissionService permissionService;

    /**
     * 创建权限
     *
     * @param request 权限请求
     * @return 权限响应
     */
    public PermissionResponse createPermission(PermissionRequest request) {
        return permissionService.createPermission(request);
    }

    /**
     * 更新权限
     *
     * @param id      权限ID
     * @param request 权限请求
     * @return 权限响应
     */
    public PermissionResponse updatePermission(Long id, PermissionRequest request) {
        return permissionService.updatePermission(id, request);
    }

    /**
     * 删除权限
     *
     * @param id 权限ID
     */
    public void deletePermission(Long id) {
        permissionService.deletePermission(id);
    }

    /**
     * 获取权限
     *
     * @param id 权限ID
     * @return 权限响应
     */
    public PermissionResponse getPermission(Long id) {
        return permissionService.getPermissionById(id);
    }

    /**
     * 分页获取权限列表
     *
     * @param pageable 分页参数
     * @return 权限响应分页
     */
    public Page<PermissionResponse> getPermissions(Pageable pageable) {
        return permissionService.getPermissions(pageable);
    }

    /**
     * 获取所有权限
     *
     * @return 权限DTO列表
     */
    public List<PermissionResponse> getAllPermissions() {
        return permissionService.getAllPermissions();
    }
}