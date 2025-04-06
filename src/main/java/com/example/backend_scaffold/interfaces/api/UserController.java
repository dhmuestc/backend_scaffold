package com.example.backend_scaffold.interfaces.api;

import com.example.backend_scaffold.application.dto.user.UserRequest;
import com.example.backend_scaffold.application.dto.user.UserResponse;
import com.example.backend_scaffold.application.dto.user.UserRoleRequest;
import com.example.backend_scaffold.interfaces.facade.UserFacade;
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
 * 用户控制器
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "用户管理相关接口")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "创建用户", description = "创建一个新的用户")
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        return userFacade.createUser(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新用户", description = "根据ID更新用户信息")
    @PreAuthorize("hasAuthority('USER_UPDATE') or #id == authentication.principal.id")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        return userFacade.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "删除用户", description = "根据ID删除用户")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public void deleteUser(@PathVariable Long id) {
        userFacade.deleteUser(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取用户", description = "根据ID获取用户信息")
    @PreAuthorize("hasAuthority('USER_READ') or #id == authentication.principal.id")
    public UserResponse getUser(@PathVariable Long id) {
        return userFacade.getUser(id);
    }

    @GetMapping
    @Operation(summary = "分页获取用户列表", description = "分页获取用户列表信息")
    @PreAuthorize("hasAuthority('USER_READ')")
    public Page<UserResponse> getUsers(Pageable pageable) {
        return userFacade.getUsers(pageable);
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有用户", description = "获取所有用户信息（简化版）")
    @PreAuthorize("hasAuthority('USER_READ')")
    public List<UserResponse> getAllUsers() {
        return userFacade.getAllUsers();
    }

    @PostMapping("/{userId}/roles")
    @Operation(summary = "分配角色给用户", description = "分配角色给指定用户")
    @PreAuthorize("hasAuthority('USER_ROLE_ASSIGN')")
    public void assignRolesToUser(@PathVariable Long userId, @Valid @RequestBody UserRoleRequest request) {
        userFacade.assignRolesToUser(userId, request);
    }

    @GetMapping("/{userId}/roles")
    @Operation(summary = "获取用户的角色", description = "获取指定用户的角色ID列表")
    @PreAuthorize("hasAuthority('USER_ROLE_READ') or #userId == authentication.principal.id")
    public List<Long> getUserRoles(@PathVariable Long userId) {
        return userFacade.getUserRoles(userId);
    }
}