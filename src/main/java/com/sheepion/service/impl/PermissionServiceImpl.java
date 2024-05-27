package com.sheepion.service.impl;

import com.sheepion.common.Result;
import com.sheepion.common.ResultCode;
import com.sheepion.dto.PermissionAssignDto;
import com.sheepion.dto.RoleCreateDto;
import com.sheepion.dto.RoleDto;
import com.sheepion.dto.UserInfoDto;
import com.sheepion.mapper.PermissionMapper;
import com.sheepion.mapper.RoleMapper;
import com.sheepion.mapper.RolePermissionMapper;
import com.sheepion.model.*;
import com.sheepion.service.PermissionService;
import com.sheepion.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Result<Permission> getPermissionById(Integer permissionId) {
        Permission permission = permissionMapper.selectByPrimaryKey(permissionId);
        if (permission == null) {
            log.warn("权限不存在 id={}", permissionId);
            return Result.failed("权限不存在");
        }
        log.info("获取权限信息: {}", permission);
        return Result.success(permission);
    }

    @Override
    @Transactional
    public Result<String> edit(PermissionAssignDto permissionAssignDto) {
        log.debug("编辑角色权限: {}", permissionAssignDto);
        //获取角色信息
        Role role = roleMapper.selectByPrimaryKey(permissionAssignDto.getRoleId());
        if (role == null) {
            log.warn("角色不存在 id={}", permissionAssignDto.getRoleId());
            throw new IllegalArgumentException("角色不存在");
        }
        //先把原有的所有权限删除
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andRoleIdEqualTo(permissionAssignDto.getRoleId());
        rolePermissionMapper.deleteByExample(example);
        //再添加新的权限，如果权限不存在，不添加
        for (Integer permissionId : permissionAssignDto.getPermissionIds()) {
            Permission permission = permissionMapper.selectByPrimaryKey(permissionId);
            if (permission == null) {
                log.warn("权限不存在 id={}", permissionId);
                continue;
            }
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(permissionAssignDto.getRoleId());
            rolePermission.setPermissionId(permissionId);
            rolePermissionMapper.insert(rolePermission);
        }
        return Result.success("编辑成功");
    }

    @Override
    public Result<Permission> getPermissionByName(String name) {
        log.debug("获取权限信息 name={}", name);
        PermissionExample example = new PermissionExample();
        example.createCriteria().andPermissionEqualTo(name);
        List<Permission> permissions = permissionMapper.selectByExample(example);
        if (permissions.size() > 0) {
            log.debug("权限存在 {}", permissions.get(0));
            return Result.success(permissions.get(0));
        }
        log.debug("权限不存在 name={}", name);
        return Result.failed("权限不存在");
    }

    @Override
    @Transactional
    public Result<String> delete(Integer roleId) {
        log.debug("删除角色: id={}", roleId);
        //删除角色的权限
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria()
                .andRoleIdEqualTo(roleId);
        rolePermissionMapper.deleteByExample(example);
        int result;
        //删除角色
        try {
            result = roleMapper.deleteByPrimaryKey(roleId);
        } catch (DataIntegrityViolationException e) {
            log.warn("删除角色失败，角色正在被使用");
            throw new RuntimeException("删除角色失败，角色正在被使用");
        }
        if (result > 0) {
            log.debug("删除角色成功 id={}", roleId);
        } else {
            log.warn("删除角色失败");
            throw new RuntimeException("删除角色失败");
        }
        log.debug("删除角色权限成功 删除了{}条数据", result);
        return Result.success("删除成功");
    }

    @Override
    @Transactional
    public Result<Integer> createRole(RoleCreateDto roleCreateDto) {
        //检查角色名是否已经存在
        RoleExample example = new RoleExample();
        example.createCriteria().andNameEqualTo(roleCreateDto.getName());
        List<Role> roles = roleMapper.selectByExample(example);
        if (roles.size() > 0) {
            log.warn("角色名已经存在 name={}", roleCreateDto.getName());
            throw new IllegalArgumentException("角色名已经存在");
        }
        //创建角色
        Role role = new Role();
        role.setName(roleCreateDto.getName());
        role.setDescription(roleCreateDto.getDescription());
        int result = roleMapper.insert(role);
        if (result > 0) {
            log.debug("创建角色成功 id={}", role.getId());
        } else {
            log.debug("创建角色失败，未知错误");
            throw new RuntimeException("创建角色失败，未知错误");
        }
        //如果对应的权限列表不为空，则分配权限
        if (roleCreateDto.getPermissionIds() != null && !roleCreateDto.getPermissionIds().isEmpty()) {
            for (Integer id : roleCreateDto.getPermissionIds()) {
                Permission permission = permissionMapper.selectByPrimaryKey(id);
                if (permission == null) {
                    log.debug("权限不存在 id={}", id);
                    continue;
                }
                //分配权限
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(id);
                rolePermissionMapper.insert(rolePermission);
            }
        }
        return Result.success(role.getId());
    }

    @Override
    public Result<String> assign(PermissionAssignDto permissionAssignDto) {
        log.debug("给角色分配权限: {}", permissionAssignDto);
        //获取角色信息
        Role role = roleMapper.selectByPrimaryKey(permissionAssignDto.getRoleId());
        if (role == null) {
            log.debug("角色不存在 id={}", permissionAssignDto.getRoleId());
            return Result.failed("角色不存在");
        }
        //给每一个权限尝试分配，如果权限不存在则跳过
        Integer[] ids = permissionAssignDto.getPermissionIds();
        for (Integer id : ids) {
            Permission permission = permissionMapper.selectByPrimaryKey(id);
            if (permission == null) {
                log.debug("权限不存在 id={}", id);
                continue;
            }
            //查询是否已经分配
            RolePermissionExample example = new RolePermissionExample();
            example.createCriteria().andRoleIdEqualTo(permissionAssignDto.getRoleId()).andPermissionIdEqualTo(id);
            List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(example);
            if (rolePermissions.size() > 0) {
                log.debug("权限已经分配 id={}", id);
                continue;
            }
            //分配权限
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(permissionAssignDto.getRoleId());
            rolePermission.setPermissionId(id);
            rolePermissionMapper.insert(rolePermission);
        }
        return Result.success("分配成功");
    }

    @Override
    public Result<List<Permission>> listPermissions() {
        List<Permission> permissions = permissionMapper.selectByExample(null);
        return Result.success(permissions);
    }

    @Override
    public Result<RoleDto> getRole(Integer roleId) {
        log.debug("查询角色: {}", roleId);
        //获取角色信息
        Role role = roleMapper.selectByPrimaryKey(roleId);
        if (role == null) {
            log.debug("角色不存在: {}", roleId);
            return Result.failed("角色不存在");
        }
        //获取角色权限
        Result<List<Permission>> listResult = getPermissionsByRoleId(roleId);
        if (listResult.getCode() != ResultCode.SUCCESS.getCode()) {
            log.debug("获取角色权限失败: {}", listResult);
            return Result.failed(listResult.getMessage());
        }
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setDescription(role.getDescription());
        roleDto.setPermissions(listResult.getData());
        return Result.success(roleDto);
    }

    @Override
    public Result<List<RoleDto>> listAll() {
        //获取所有角色
        List<Role> roles = roleMapper.selectByExample(null);
        //获取dto
        List<RoleDto> dtos = roles.stream().map(role -> {
            Result<RoleDto> dtoResult = getRole(role.getId());
            if (dtoResult.getCode() != ResultCode.SUCCESS.getCode()) {
                log.debug("获取角色失败: {}", role.getId());
                return null;
            }
            return dtoResult.getData();
        }).collect(Collectors.toList());
        return Result.success(dtos);
    }

    @Override
    public Result<List<Permission>> getPermissionsByUserId(Integer userId) {
        log.debug("查询用户权限: {}", userId);
        //获取用户的角色id
        Result<UserInfoDto> userInfoDtoResult = userService.getAdministratorInfoById(userId);
        if (userInfoDtoResult.getCode() != ResultCode.SUCCESS.getCode()) {
            log.debug("获取用户信息失败: {}", userInfoDtoResult);
            return Result.failed("获取用户信息失败");
        }
        UserInfoDto userInfoDto = userInfoDtoResult.getData();
        return getPermissionsByRoleId(Integer.valueOf(userInfoDto.getRoleId()));
    }

    @Override
    public Result<List<Permission>> getPermissionsByRoleId(Integer roleId) {
        log.debug("查询角色权限: {}", roleId);
        //获取角色拥有的权限id
        RolePermissionExample rolePermissionExample = new RolePermissionExample();
        rolePermissionExample.createCriteria().andRoleIdEqualTo(roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(rolePermissionExample);
        List<Integer> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId).collect(java.util.stream.Collectors.toList());
        log.debug("角色权限id: {}", permissionIds);
        //根据权限id获取权限
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andIdIn(permissionIds);
        List<Permission> permissions = permissionMapper.selectByExample(permissionExample);
        log.debug("角色权限: {}", permissions);
        return Result.success(permissions);
    }
}
