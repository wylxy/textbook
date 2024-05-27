package com.sheepion.service.impl;

import com.sheepion.common.Result;
import com.sheepion.common.ResultCode;
import com.sheepion.common.UserHolder;
import com.sheepion.dto.*;
import com.sheepion.enums.Role;
import com.sheepion.mapper.AdministratorMapper;
import com.sheepion.mapper.LogMapper;
import com.sheepion.model.Administrator;
import com.sheepion.model.AdministratorExample;
import com.sheepion.model.Log;
import com.sheepion.model.Permission;
import com.sheepion.service.PermissionService;
import com.sheepion.service.StreetService;
import com.sheepion.service.UserService;
import com.sheepion.util.JwtUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private AdministratorMapper administratorMapper;
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private PermissionService permissionService;

    @Override
    public Result<List<UserInfoDto>> getAllUser(String name) {
        log.debug("获取所有用户信息");
        AdministratorExample example = new AdministratorExample();
        if (name != null) {
            example.createCriteria().andUsernameLike("%" + name + "%");
        }
        List<Administrator> administrators = administratorMapper.selectByExample(example);
        List<UserInfoDto> userInfoDtos = administrators.stream().map(this::convertToDto).collect(Collectors.toList());
        return Result.success(userInfoDtos);
    }

    @Override
    public Result<Integer> count() {
        log.debug("获取用户数量");
        int result = (int)administratorMapper.countByExample(new AdministratorExample());
        return Result.success(result);
    }

    @Override
    public Result<String> login(String username, String password) {
        Log log1 = new Log();
        log.debug("用户登录：{} {}", username, password);
        AdministratorExample example = new AdministratorExample();
        example.createCriteria()
                .andUsernameEqualTo(username)
                .andPasswordEqualTo(password);
        List<Administrator> users = administratorMapper.selectByExample(example);
        log1.setManagerId(username);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String format = df.format(date);
        log1.setTime(format);
        if (CollectionUtils.isEmpty(users)) {
            log.debug("用户名或密码错误");
            log1.setLogMessage("登录（用户名或密码错误)");
            logMapper.insert(log1);
            return Result.failed("用户名或密码错误");
        }
        Administrator user = users.get(0);
        //获取用户权限列表
        Result<List<Permission>> permissionResult = permissionService.getPermissionsByRoleId(Integer.valueOf(user.getRole()));
        List<Permission> permissions=new ArrayList<>();
        if (permissionResult.getCode() == ResultCode.SUCCESS.getCode()) {
            permissions = permissionResult.getData();
            log.debug("获取用户权限成功: {}", permissions);
        }else{
            log.debug("获取用户权限失败: {}", permissionResult.getMessage());
        }
        List<String> permissionList = permissions.stream().map(Permission::getPermission).collect(Collectors.toList());
        //生成token
        String token = JwtUtil.generateToken(user.getId(), user.getRole(),permissionList);
        log1.setLogMessage("登录（成功)");
        logMapper.insert(log1);
        log.debug("登录成功，token：{}", token);
        return Result.success(token);
    }

    @Override
    public Result<UserInfoDto> getAdministratorInfoById(Integer id) {
        log.debug("获取用户信息：{}", id);
        //如果id为null，从token中获取用户信息
        if (id == null) {
            log.debug("id为null，从token中获取用户信息");
            id = UserHolder.getCurrentUser();
        }
        Administrator administrator = administratorMapper.selectByPrimaryKey(id);
        //查询失败，返回错误信息
        if (administrator == null) {
            log.debug("用户不存在");
            return Result.failed("用户不存在");
        }
        UserInfoDto userInfoDto = convertToDto(administrator);
        log.debug("用户信息：{}", userInfoDto);
        return Result.success(userInfoDto);
    }

    @Override
    public Result<String> register(RegisterDto registerParam) {
        log.debug("用户注册：{}", registerParam);
        //检查参数是否符合要求
//        if (!checkRegisterParam(registerParam)) {
//            return Result.failed("注册参数不符合要求");
//        }
        //检查用户名是否已经存在
        AdministratorExample administratorExample = new AdministratorExample();
        AdministratorExample.Criteria criteria = administratorExample.createCriteria();
        criteria.andUsernameEqualTo(registerParam.getUsername());
        if (administratorMapper.countByExample(administratorExample) > 0) {
            log.debug("用户名已存在");
            return Result.failed("用户名已存在");
        }
        //TODO: 检查角色id和街道id是否存在

        //添加到数据库
        Administrator administrator = new Administrator();
        BeanUtils.copyProperties(registerParam, administrator);
        log.debug("注册用户信息：{}", administrator);
        int result = administratorMapper.insert(administrator);
        if (result > 0) {
            log.debug("注册成功: {}", administrator);
            return Result.success("注册成功");
        }
        log.debug("注册失败: {}", administrator);
        return Result.failed("注册失败，未知错误");
    }

    @Override
    public Result<String> updateInfoById(UserInfoDto userInfoDto) {
        int result = 0;
        Administrator administrator = new Administrator();
        //检查用户是否存在
        List<Administrator> administrators = administratorMapper.selectByName(userInfoDto.getUsername(),userInfoDto.getPassword());
        if (administrators.size()<=0) {
            log.debug("原始密码错误：{}", userInfoDto);
            return Result.failed("原始密码错误");
        }else{
            administrator.setUsername(userInfoDto.getUsername());
            administrator.setPassword(userInfoDto.getNewPass());
            result = administratorMapper.updateByName(administrator);
        }
        if (result > 0) {
            log.debug("更新成功: {}", administrator);
            return Result.success("更新成功");
        }
        log.debug("更新失败: {}", administrator);
        return Result.failed("更新失败，未知错误");
    }

    @Override
    public Result<String> updatePassword(String password) {
        log.debug("更新密码：{}", password);
        //获取当前登录的用户id
        Integer userId = UserHolder.getCurrentUser();
        if (userId == null) {
            log.debug("用户未登录");
            return Result.failed("用户未登录");
        }
        log.debug("当前登录用户id：{}", userId);
        //更新密码
        AdministratorExample example = new AdministratorExample();
        example.createCriteria()
                .andIdEqualTo(userId);
        Administrator administrator = new Administrator();
        administrator.setPassword(password);
        int result = administratorMapper.updateByExampleSelective(administrator, example);
        if (result > 0) {
            log.debug("更新成功");
            return Result.success("更新成功");
        }
        log.debug("更新失败");
        return Result.failed("更新失败，未知错误");
    }
    @Override
    public Result<String> delete(RegisterDto registerParam) {
        AdministratorExample example = new AdministratorExample();
        example.createCriteria()
                .andUsernameEqualTo(registerParam.getUsername());
        int result = administratorMapper.deleteByExample(example);
        if (result > 0) {
            log.debug("删除成功");
            return Result.success("删除成功");
        }
        log.debug("删除失败");
        return Result.failed("删除失败，未知错误");
    }
    @Override
    public Result<String> updateRole(RoleUpdateDto roleUpdateDto) {
        //判断当前登录用户是不是管理员
        Integer userId = UserHolder.getCurrentUser();
        if (userId == null) {
            log.debug("用户未登录");
            return Result.failed(ResultCode.UNAUTHORIZED, "用户未登录");
        }
        Administrator administrator = administratorMapper.selectByPrimaryKey(userId);
        if (administrator == null) {
            log.debug("用户不存在");
            return Result.failed(ResultCode.UNAUTHORIZED, "用户不存在");
        }
        if (administrator.getRole() != Role.ADMINISTRATOR.getCode()) {
            log.debug("当前登录用户不是管理员");
            return Result.failed(ResultCode.FORBIDDEN, "当前登录用户不是管理员");
        }
        //判断要修改的用户是否存在
        Administrator administrator1 = administratorMapper.selectByPrimaryKey(roleUpdateDto.getId());
        if (administrator1 == null) {
            log.debug("要修改的用户不存在");
            return Result.failed(ResultCode.VALIDATE_FAILED, "要修改的用户不存在");
        }
        //判断要修改的角色是否存在
        Role role = Role.getRoleByCode(roleUpdateDto.getRoleId());
        if (role == null) {
            log.debug("要修改的角色不存在");
            return Result.failed(ResultCode.VALIDATE_FAILED, "要修改的角色不存在");
        }
        //修改角色
        administrator1.setRole(role.getCode());
        int result = administratorMapper.updateByPrimaryKey(administrator1);
        if (result > 0) {
            log.debug("修改成功");
            return Result.success("修改成功");
        }
        log.debug("修改失败");
        return Result.failed("修改失败，未知错误");
    }

    @Override
    public Result<List<UserInfoDto>> list(String name, Integer page, Integer pageSize) {
        log.debug("查询用户信息列表：name = {}, page = {}, pageSize = {}", name, page, pageSize);
        if (name != null) {
            name = "%" + name + "%";
        }
        if(page==null||page<=0){
            page=1;
        }
        if(pageSize==null||pageSize<=0){
            pageSize=10;
        }
        int offset=(page-1)*pageSize;
        List<Administrator> administrators = administratorMapper.selectByPage(name, offset, pageSize);
        if (administrators == null || administrators.size() == 0) {
            log.debug("查询结果为空");
            return Result.success(null);
        }
        List<UserInfoDto> userInfoDtos = administrators.stream()
                .map(this::convertToDto).collect(Collectors.toList());
        log.debug("查询结果：{}", userInfoDtos);
        return Result.success(userInfoDtos);
    }

    private UserInfoDto convertToDto(Administrator model) {
        UserInfoDto dto = new UserInfoDto();
        BeanUtils.copyProperties(model, dto);
        //设置角色、角色id、街道、街道id
        dto.setRoleId(model.getRole());
        Result<RoleDto> roleDtoResult = permissionService.getRole(Integer.valueOf(model.getRole()));
        if(roleDtoResult.getCode()!=ResultCode.SUCCESS.getCode()){
            log.error("获取角色信息失败：{}",roleDtoResult);
            dto.setRole("未知");
        }else{
            dto.setRole(roleDtoResult.getData().getName());
        }
        dto.setStreetId(model.getStreetId());
//        Result<StreetDto> streetDtoResult = streetService.getById(model.getStreetId());
//        if(streetDtoResult.getCode()!=ResultCode.SUCCESS.getCode()){
//            log.error("获取街道信息失败：{}",streetDtoResult);
//            dto.setStreet("未知");
//        }else{
//            dto.setStreet(streetDtoResult.getData().getName());
//        }
        return dto;
    }

    /**
     * 修改用户信息时使用，检验用户信息参数是否符合要求
     *
     * @param userInfoParam 用户信息参数
     * @return 符合要求返回true，否则返回false
     */
    public static boolean checkUserInfoParam(UserInfoDto userInfoParam) {
        //检查用户名是否符合要求
        //用户名长度在3-10之间
        if (userInfoParam.getUsername() == null
                || userInfoParam.getUsername().length() < 3
                || userInfoParam.getUsername().length() > 10) {
            return false;
        }
        //姓名不超过15个字符
        if (userInfoParam.getName() == null
                || userInfoParam.getName().length() < 1
                || userInfoParam.getName().length() > 15) {
            return false;
        }
        //检查其他字段是否为空字符或者null
        if (userInfoParam.getContactInfo() == null
                || "".equals(userInfoParam.getContactInfo())
                || userInfoParam.getRoleId() == null
                || userInfoParam.getStreetId() == null) {
            return false;
        }
        //使用正则表达式来检验手机号码合法性
        if (!userInfoParam.getContactInfo().matches(
                "^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$")) {
            return false;
        }
        return true;
    }

    /**
     * 检验注册参数是否符合要求
     *
     * @param registerParam 注册参数
     * @return 符合要求返回true，否则返回false
     */
    public static boolean checkRegisterParam(RegisterDto registerParam) {
        //检查用户名是否符合要求
        //用户名长度在3-10之间
        if (registerParam.getUsername() == null
                || registerParam.getUsername().length() < 3
                || registerParam.getUsername().length() > 10) {
            return false;
        }
        //姓名不超过15个字符
        if (registerParam.getName() == null
                || registerParam.getName().length() < 1
                || registerParam.getName().length() > 15) {
            return false;
        }
        //检查其他字段是否为空字符或者null
        if (registerParam.getPassword() == null
                || "".equals(registerParam.getPassword())
                || registerParam.getContactInfo() == null
                || "".equals(registerParam.getContactInfo())
                || registerParam.getRole() == null
                ) {
            return false;
        }
        //使用正则表达式来检验手机号码合法性
        if (!registerParam.getContactInfo().matches(
                "^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$")) {
            return false;
        }
        return true;
    }
}
