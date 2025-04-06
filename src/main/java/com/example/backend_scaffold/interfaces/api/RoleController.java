package com.example.backend_scaffold.interfaces.api;

import com.example.backend_scaffold.application.dto.role.RolePermissionRequest;
import com.example.backend_scaffold.application.dto.role.RoleRequest;
import com.example.backend_scaffold.application.dto.role.RoleResponse;
import com.example.backend_scaffold.interfaces.facade.RoleFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 角色控制器
 */
@RestController
@RequestMapping("/api/roles")
@Tag(name = "角色管理", description = "角色管理相关接口")
@RequiredArgsConstructor
public class RoleController {

    private final RoleFacade roleFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "创建角色", description = "创建一个新的角色")
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public RoleResponse createRole(@Valid @RequestBody RoleRequest request) {
        return roleFacade.createRole(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新角色", description = "根据ID更新角色信息")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public RoleResponse updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequest request) {
        return roleFacade.updateRole(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "删除角色", description = "根据ID删除角色")
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public void deleteRole(@PathVariable Long id) {
        roleFacade.deleteRole(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取角色", description = "根据ID获取角色信息")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public RoleResponse getRole(@PathVariable Long id) {
        return roleFacade.getRole(id);
    }

    @GetMapping
    @Operation(summary = "分页获取角色列表", description = "分页获取角色列表信息")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public Page<RoleResponse> getRoles(Pageable pageable) {
        return roleFacade.getRoles(pageable);
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有角色", description = "获取所有角色信息（简化版）")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public List<RoleResponse> getAllRoles() {
        return roleFacade.getAllRoles();
    }

    @PostMapping("/{roleId}/permissions")
    @Operation(summary = "分配权限给角色", description = "分配权限给指定角色")
    @PreAuthorize("hasAuthority('ROLE_PERMISSION_ASSIGN')")
    public void assignPermissionsToRole(@PathVariable Long roleId, @Valid @RequestBody RolePermissionRequest request) {
        roleFacade.assignPermissionsToRole(roleId, request);
    }

    @GetMapping("/{roleId}/permissions")
    @Operation(summary = "获取角色的权限", description = "获取指定角色的权限ID列表")
    @PreAuthorize("hasAuthority('ROLE_PERMISSION_READ')")
    public List<Long> getRolePermissions(@PathVariable Long roleId) {
        return roleFacade.getRolePermissions(roleId);
    }
}