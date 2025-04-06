package com.example.backend_scaffold.interfaces.facade;

import com.example.backend_scaffold.application.dto.role.RolePermissionRequest;
import com.example.backend_scaffold.application.dto.role.RoleRequest;
import com.example.backend_scaffold.application.dto.role.RoleResponse;
import com.example.backend_scaffold.application.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 角色外观类，作为应用服务的门面
 */
@Component
@RequiredArgsConstructor
public class RoleFacade {

    private final RoleService roleService;

    /**
     * 创建角色
     *
     * @param request 角色请求
     * @return 角色响应
     */
    public RoleResponse createRole(RoleRequest request) {
        return roleService.createRole(request);
    }

    /**
     * 更新角色
     *
     * @param id      角色ID
     * @param request 角色请求
     * @return 角色响应
     */
    public RoleResponse updateRole(Long id, RoleRequest request) {
        return roleService.updateRole(id, request);
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     */
    public void deleteRole(Long id) {
        roleService.deleteRole(id);
    }

    /**
     * 获取角色
     *
     * @param id 角色ID
     * @return 角色响应
     */
    public RoleResponse getRole(Long id) {
        return roleService.getRoleById(id);
    }

    /**
     * 分页获取角色列表
     *
     * @param pageable 分页参数
     * @return 角色响应分页
     */
    public Page<RoleResponse> getRoles(Pageable pageable) {
        return roleService.getRoles(pageable);
    }

    /**
     * 获取所有角色
     *
     * @return 角色DTO列表
     */
    public List<RoleResponse> getAllRoles() {
        return roleService.getAllRoles();
    }

    /**
     * 分配权限给角色
     *
     * @param roleId  角色ID
     * @param request 角色权限请求
     */
    public void assignPermissionsToRole(Long roleId, RolePermissionRequest request) {
        roleService.assignPermissionsToRole(roleId, request);
    }

    /**
     * 获取角色的权限
     *
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    public List<Long> getRolePermissions(Long roleId) {
        return roleService.getRolePermissions(roleId);
    }
}