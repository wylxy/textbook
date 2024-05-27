package com.sheepion.service;

import com.sheepion.common.Result;
import com.sheepion.dto.PermissionAssignDto;
import com.sheepion.dto.RoleCreateDto;
import com.sheepion.dto.RoleDto;
import com.sheepion.model.Permission;

import java.util.List;

public interface PermissionService {
    /**
     * 获取角色的所有权限
     * @param roleId 角色id
     * @return 权限列表
     */
    Result<List<Permission>> getPermissionsByRoleId(Integer roleId);

    /**
     * 获取用户的所有权限
     * @param userId 用户id
     * @return 权限列表
     */
    Result<List<Permission>> getPermissionsByUserId(Integer userId);

    /**
     * 根据角色id获取角色信息，包含角色的权限列表
     * @param roleId 角色id
     * @return 角色信息
     */
    Result<RoleDto> getRole(Integer roleId);

    /**
     * 获取角色列表，包含角色的权限列表
     * @return 角色列表
     */
    Result<List<RoleDto>> listAll();

    /**
     * 列出所有可用的权限列表
     * @return 权限列表
     */
    Result<List<Permission>> listPermissions();

    /**
     * 给角色分配权限
     * @param permissionAssignDto 角色id和权限id列表
     * @return 操作结果
     */
    Result<String> assign(PermissionAssignDto permissionAssignDto);

    /**
     * 创建角色
     * @param roleCreateDto 角色名和角色描述
     * @return 成功则返回角色id
     */
    Result<Integer> createRole(RoleCreateDto roleCreateDto);

    /**
     * 删除角色
     * @param roleId 角色id
     * @return 操作结果
     */
    Result<String> delete(Integer roleId);

    /**
     * 根据权限名获取权限信息<br/>
     * 例如，权限名为"application.handle"，则返回的权限信息中的permission为"application.handle"
     * @param name 权限名
     * @return 权限信息
     */
    Result<Permission> getPermissionByName(String name);

    /**
     * 编辑角色的权限
     * @param permissionAssignDto 角色id和权限id列表
     * @return 操作结果
     */
    Result<String> edit(PermissionAssignDto permissionAssignDto);

    /**
     * 根据权限id获取权限信息
     * @param permissionId 权限id
     * @return 权限信息
     */
    Result<Permission> getPermissionById(Integer permissionId);
}
