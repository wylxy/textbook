package com.sheepion.service;

import com.sheepion.common.Result;
import com.sheepion.dto.RegisterDto;
import com.sheepion.dto.RoleUpdateDto;
import com.sheepion.dto.UserInfoDto;

import java.util.List;


public interface UserService {
    /**
     * 通过id获取用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    Result<UserInfoDto> getAdministratorInfoById(Integer id);

    /**
     * 注册用户
     *
     * @param registerParam 注册参数
     * @return 注册结果
     */
    Result<String> register(RegisterDto registerParam);
    Result<String> delete(RegisterDto registerParam);

    /**
     * 根据用户id更新用户基本信息
     *
     * @param userInfoDto 用户基本信息
     * @return 更新结果
     */

    Result<String> updateInfoById(UserInfoDto userInfoDto);

    /**
     * 修改当前登录的用户的密码
     *
     * @param password 新密码
     * @return 修改结果
     */

    Result<String> updatePassword(String password);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 成功返回token，失败返回错误信息
     */
    Result<String> login(String username, String password);

    /**
     * 给用户分配角色，只有当前登录的用户是管理员才能分配角色
     * @param roleUpdateDto 参数
     * @return 分配结果
     */
    Result<String> updateRole(RoleUpdateDto roleUpdateDto);

    /**
     * 分页模糊查询用户列表
     * @param name 姓名
     * @param page 页码
     * @param pageSize 每页大小
     * @return 用户列表
     */
    Result<List<UserInfoDto>> list(String name, Integer page, Integer pageSize);

    /**
     * 统计用户数量
     * @return 用户数量
     */
    Result<Integer> count();

    /**
     * 返回所有用户信息
     * @return 所有用户信息
     */
    Result<List<UserInfoDto>> getAllUser(String name);
}
