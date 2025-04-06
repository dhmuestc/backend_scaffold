-- 初始化权限数据
INSERT INTO permissions (name, description, type, created_at, updated_at) VALUES
-- 用户权限
('user:create', '创建用户', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('user:read', '查看用户', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('user:update', '更新用户', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('user:delete', '删除用户', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('user:list', '用户列表', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),

-- 角色权限
('role:create', '创建角色', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('role:read', '查看角色', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('role:update', '更新角色', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('role:delete', '删除角色', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('role:list', '角色列表', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),

-- 权限管理
('permission:create', '创建权限', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('permission:read', '查看权限', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('permission:update', '更新权限', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('permission:delete', '删除权限', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('permission:list', '权限列表', 'FUNCTION', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),

-- 菜单权限
('menu:user', '用户管理菜单', 'MENU', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('menu:role', '角色管理菜单', 'MENU', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('menu:permission', '权限管理菜单', 'MENU', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('menu:system', '系统管理菜单', 'MENU', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- 初始化角色数据
INSERT INTO roles (name, description, created_at, updated_at) VALUES
('ROLE_ADMIN', '系统管理员', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('ROLE_USER', '普通用户', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
('ROLE_GUEST', '访客', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- 获取角色ID
SET @admin_role_id = (SELECT id FROM roles WHERE name = 'ROLE_ADMIN');
SET @user_role_id = (SELECT id FROM roles WHERE name = 'ROLE_USER');
SET @guest_role_id = (SELECT id FROM roles WHERE name = 'ROLE_GUEST');

-- 为管理员角色分配所有权限
INSERT INTO role_permissions (role_id, permission_id, created_at, updated_at)
SELECT @admin_role_id, id, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()
FROM permissions;

-- 为普通用户角色分配基本权限
INSERT INTO role_permissions (role_id, permission_id, created_at, updated_at)
SELECT @user_role_id, id, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()
FROM permissions
WHERE name IN ('user:read', 'user:update', 'role:read', 'permission:read', 'menu:user');

-- 为访客角色分配只读权限
INSERT INTO role_permissions (role_id, permission_id, created_at, updated_at)
SELECT @guest_role_id, id, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()
FROM permissions
WHERE name IN ('user:read', 'role:read', 'permission:read');

-- 初始化管理员用户 (密码: admin