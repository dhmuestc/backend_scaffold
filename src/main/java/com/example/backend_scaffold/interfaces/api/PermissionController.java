package com.example.backend_scaffold.interfaces.api;

import com.example.backend_scaffold.application.dto.permission.PermissionRequest;
import com.example.backend_scaffold.application.dto.permission.PermissionResponse;
import com.example.backend_scaffold.interfaces.facade.PermissionFacade;
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
 * 权限控制器
 */
@RestController
@RequestMapping("/api/permissions")
@Tag(name = "权限管理", description = "权限管理相关接口")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionFacade permissionFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "创建权限", description = "创建一个新的权限")
    @PreAuthorize("hasAuthority('PERMISSION_CREATE')")
    public PermissionResponse createPermission(@Valid @RequestBody PermissionRequest request) {
        return permissionFacade.createPermission(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新权限", description = "根据ID更新权限信息")
    @PreAuthorize("hasAuthority('PERMISSION_UPDATE')")
    public PermissionResponse updatePermission(@PathVariable Long id, @Valid @RequestBody PermissionRequest request) {
        return permissionFacade.updatePermission(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "删除权限", description = "根据ID删除权限")
    @PreAuthorize("hasAuthority('PERMISSION_DELETE')")
    public void deletePermission(@PathVariable Long id) {
        permissionFacade.deletePermission(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取权限", description = "根据ID获取权限信息")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public PermissionResponse getPermission(@PathVariable Long id) {
        return permissionFacade.getPermission(id);
    }

    @GetMapping
    @Operation(summary = "分页获取权限列表", description = "分页获取权限列表信息")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public Page<PermissionResponse> getPermissions(Pageable pageable) {
        return permissionFacade.getPermissions(pageable);
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有权限", description = "获取所有权限信息（简化版）")
    @PreAuthorize("hasAuthority('PERMISSION_READ')")
    public List<PermissionResponse> getAllPermissions() {
        return permissionFacade.getAllPermissions();
    }
}