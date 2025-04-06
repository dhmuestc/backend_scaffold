# 后端脚手架项目

## 项目简介

这是一个基于Spring Boot 3的后端脚手架项目，采用领域驱动设计(DDD)架构，实现了基于角色的访问控制(RBAC)权限模型。该项目旨在为企业级应用提供一个可扩展、安全且结构清晰的后端系统基础框架。

## 技术栈

- **Spring Boot 3.1.5**: 应用程序核心框架
- **Spring Security**: 安全认证与授权
- **Spring Data JPA**: 数据持久化
- **MariaDB Database**: 数据库
- **JWT**: 基于令牌的认证
- **Lombok**: 减少样板代码
- **MapStruct**: 对象映射
- **SpringDoc OpenAPI**: API文档生成

## 项目架构

项目采用领域驱动设计(DDD)架构，分为以下几层：

### 领域层 (Domain Layer)

- **聚合根(Aggregate Root)**: 定义领域对象的边界
- **实体(Entity)**: 具有唯一标识的领域对象
- **值对象(Value Object)**: 没有唯一标识的领域对象
- **领域服务(Domain Service)**: 处理跨实体的业务逻辑

### 应用层 (Application Layer)

- **应用服务(Application Service)**: 协调领域对象完成用户用例
- **DTO(Data Transfer Object)**: 数据传输对象
- **装配器(Assembler)**: 在DTO和领域对象之间转换

### 基础设施层 (Infrastructure Layer)

- **持久化(Persistence)**: 数据库访问实现
- **安全(Security)**: 认证和授权实现
- **缓存(Cache)**: 缓存实现

### 接口层 (Interface Layer)

- **控制器(Controller)**: 处理HTTP请求和响应

## RBAC权限模型

系统实现了基于角色的访问控制(RBAC)权限模型，主要包括：

- **用户(User)**: 系统用户
- **角色(Role)**: 用户角色，如管理员、普通用户等
- **权限(Permission)**: 具体操作权限，如读取、创建、更新、删除等

用户可以被分配多个角色，角色可以被授予多个权限，从而实现灵活的权限控制。

## 如何使用

### 环境要求

- JDK 17+
- Maven 3.6+

### 构建与运行

```bash
# 克隆项目
git clone https://github.com/example/backend_scaffold.git

# 进入项目目录
cd backend_scaffold

# 构建项目
./mvnw clean package

# 运行项目
./mvnw spring-boot:run
```

### API文档

启动应用后，可以通过以下URL访问API文档：

```
http://localhost:8080/swagger-ui.html
```

## 许可证

[MIT](LICENSE)