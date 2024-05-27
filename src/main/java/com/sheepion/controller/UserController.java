package com.sheepion.controller;

import com.sheepion.common.Result;
import com.sheepion.dto.RegisterDto;
import com.sheepion.dto.RoleUpdateDto;
import com.sheepion.dto.UserInfoDto;
import com.sheepion.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@Api(value = "UserController", tags = "用户模块")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "测试接口", notes = "测试接口")
    @GetMapping(value = "/test")
    @ResponseBody
    public Result<UserInfoDto> test() {
        log.debug("测试接口");
        return userService.getAdministratorInfoById(1);
    }
    @ApiOperation(value = "按名字模糊获取所有用户信息")
    @GetMapping(value = "/all")
    public Result<List<UserInfoDto>> getAllUser(@RequestParam(required = false) String name) {
        return userService.getAllUser(name);
    }

    @ApiOperation(value = "用户登录", notes = "登录成功返回token，失败返回错误信息")
    @GetMapping(value = "/login")
    @ResponseBody
    public Result<String> login(@ApiParam(value = "用户名", required = true) @RequestParam String username,
                                @ApiParam(value = "密码", required = true) @RequestParam String password) {
        System.out.println(username);
        return userService.login(username, password);
    }

    @ApiOperation("添加新的账号")
    @ResponseBody
    @PostMapping(value = "/register")
    /**
     * 不开放注册，账号的添加需要管理员（有权限的用户）手动添加
     */
    public Result<String> register(@RequestBody RegisterDto registerParam) {
        return userService.register(registerParam);
    }
    @ApiOperation("注销")
    @ResponseBody
    @PostMapping(value = "/delete")
    public Result<String> delete(@RequestBody RegisterDto registerParam) {
        return userService.delete(registerParam);
    }
    @ApiOperation(value = "获取用户基本信息",notes = "根据id获取用户基本信息，不传id则获取当前token对应的用户信息")
    @ResponseBody
    @GetMapping("/info")
    public Result<UserInfoDto> info(@RequestParam(required = false) Integer id) {
        return userService.getAdministratorInfoById(id);
    }

    @ApiOperation("修改用户基本信息")
    @ResponseBody
    @PostMapping("/edit")
    /**
     * 修改包括用户角色在内的所有信息
     * 对于用户角色的修改，需要管理员（有权限的用户）手动修改，用户无法自行修改
     */
    public Result<String> updateInfo(@RequestBody UserInfoDto userInfoDto) {
        return userService.updateInfoById(userInfoDto);
    }

    @ApiOperation(value = "修改密码", notes = "只需要传入新密码即可，修改当前token对应的用户的密码")
    @ResponseBody
    @PutMapping("/password")
    public Result<String> updatePassword(@RequestBody String password) {
        return userService.updatePassword(password);
    }

    @ApiOperation(value = "给用户分配角色", notes = "只有管理员才能分配角色")
    @ResponseBody
    @PutMapping("/role")
    public Result<String> updateRole(@RequestBody RoleUpdateDto roleUpdateDto) {
        return userService.updateRole(roleUpdateDto);
    }

    @ApiOperation("分页模糊查询用户列表")
    @GetMapping("/list")
    public Result<List<UserInfoDto>> list(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize) {
        return userService.list(name, page, pageSize);
    }

    @ApiOperation("统计用户数量")
    @GetMapping("/count")
    public Result<Integer> count() {
        return userService.count();
    }
}
