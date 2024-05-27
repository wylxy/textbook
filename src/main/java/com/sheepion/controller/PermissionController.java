package com.sheepion.controller;

import com.sheepion.common.HasPermission;
import com.sheepion.common.Result;
import com.sheepion.common.UserHolder;
import com.sheepion.dto.PermissionAssignDto;
import com.sheepion.dto.RoleCreateDto;
import com.sheepion.dto.RoleDto;
import com.sheepion.model.Permission;
import com.sheepion.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "PermissionController", tags = "权限管理")
@RestController
@Slf4j
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "权限测试接口",notes = "需要permission.test才能调用")
    @GetMapping("/test")
    @HasPermission("permission.test")
    public Result<List<String>> test() {
        return Result.success(UserHolder.getCurrentPermissions());
    }

    @ApiOperation("给角色分配权限")
    @PostMapping("/assign")
    @HasPermission("permission.assign")
    public Result<String> assign(@RequestBody PermissionAssignDto permissionAssignDto) {
        return permissionService.assign(permissionAssignDto);
    }

    @ApiOperation("编辑角色的权限")
    @PutMapping("/edit")
    @HasPermission("permission.edit")
    public Result<String> edit(@RequestBody PermissionAssignDto permissionAssignDto) {
        return permissionService.edit(permissionAssignDto);
    }
    @ApiOperation("删除角色")
    @DeleteMapping("/delete")
    @HasPermission("permission.delete")
    public Result<String> delete(@RequestParam Integer id) {
        return permissionService.delete(id);
    }
    @ApiOperation(value = "创建角色",notes = "成功则返回新角色的id")
    @PostMapping("/createRole")
    @HasPermission("permission.create-role")
    public Result<Integer> createRole(@RequestBody RoleCreateDto roleCreateDto) {
        return permissionService.createRole(roleCreateDto);
    }
    @ApiOperation("获取角色列表，包含角色的权限列表")
    @GetMapping("/listall")
    @HasPermission("permission.list-all")
    public Result<List<RoleDto>> listAll() {
        return permissionService.listAll();
    }

    @ApiOperation(value = "获取用户的所有权限", notes = "不填写参数则根据token获取当前用户的权限")
    @GetMapping("/list")
    @HasPermission("permission.list-all")
    public Result<List<Permission>> listByUserId(@RequestParam(required = false) Integer userId) {
        log.debug("获取用户的所有权限: {}",userId);
        if (userId == null) {
            userId = UserHolder.getCurrentUser();
            log.debug("获取当前用户的所有权限 {}",userId);
        }
        return permissionService.getPermissionsByUserId(userId);
    }

    @GetMapping("/listByRole")
    @ApiOperation(value = "根据角色id获取权限列表")
    @HasPermission("permission.list-all")
    public Result<List<Permission>> listByRoleId(@RequestParam Integer roleId) {
        return permissionService.getPermissionsByRoleId(roleId);
    }
    @GetMapping("/role-info")
    @ApiOperation(value = "根据角色id获取角色信息")
    @HasPermission("permission.list-all")
    public Result<RoleDto> getRoleInfo(@RequestParam Integer roleId) {
        return permissionService.getRole(roleId);
    }

    @ApiOperation(value = "获取所有可用的权限列表", notes = "用户没有的权限也会列出来")
    @GetMapping("/listPermissions")
    @HasPermission("permission.list-all")
    public Result<List<Permission>> listPermissions() {
        return permissionService.listPermissions();
    }
}
