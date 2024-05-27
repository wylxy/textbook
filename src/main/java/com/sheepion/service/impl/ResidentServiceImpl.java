package com.sheepion.service.impl;

import com.sheepion.common.Result;
import com.sheepion.common.ResultCode;
import com.sheepion.common.UserHolder;
import com.sheepion.dto.CustomerDto;
import com.sheepion.dto.ResidentCreateDto;
import com.sheepion.dto.UserInfoDto;
import com.sheepion.enums.Role;
import com.sheepion.mapper.ResidentMapper;
import com.sheepion.model.Administrator;
import com.sheepion.model.Customer;
import com.sheepion.model.ResidentExample;
import com.sheepion.service.ResidentService;
import com.sheepion.service.StreetService;
import com.sheepion.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResidentServiceImpl implements ResidentService {
    @Autowired
    private ResidentMapper residentMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private StreetService streetService;

    @Override
    public Result<List<CustomerDto>> getAll(String name) {
        log.debug("获取所有居民信息");
        ResidentExample example = new ResidentExample();
        if (name != null) {
            example.createCriteria().andNameLike("%" + name + "%");
        }
        List<Customer> residents = residentMapper.selectByExample(example);
        if (residents == null) {
            return Result.failed("获取居民信息失败");
        }
        List<CustomerDto> dtos=residents.stream().map(this::convertToDto).collect(Collectors.toList());
        return Result.success(dtos);
    }

    @Override
    public Result<String> deleteResident(Integer id) {
        log.debug("删除客户信息，id = {}", id);
        //获取当前登录的用户信息
//        Result<UserInfoDto> userInfoDtoResult = userService.getAdministratorInfoById(UserHolder.getCurrentUser());
//        if (userInfoDtoResult.getCode() != ResultCode.SUCCESS.getCode()) {
//            log.debug("获取客户信息失败");
//            return Result.failed("获取客户信息失败");
//        }
//        UserInfoDto administrator = userInfoDtoResult.getData();
//        log.debug("当前登录用户信息：{}", administrator);
        //查询居民是否存在
//        Customer resident = residentMapper.selectByPrimaryKey(id);
//        if (resident == null) {
//            log.debug("客户不存在 id = {}", id);
//            return Result.failed("居民不存在");
//        }
//        log.debug("居民信息：{}", resident);
        //如果当前登录的用户是街道工作人员，只能删除自己街道的居民
//        if (administrator.getRoleId() == Role.STREET_STAFF.getCode()) {
//            if(!Objects.equals(resident.getStreetId(), administrator.getStreetId())){
//                log.debug("无权限删除该居民 id = {}", id);
//                return Result.failed("无权限删除该居民");
//            }
//        }
        //删除居民
        int result = residentMapper.deleteByPrimaryKey(id);
        if (result > 0) {
            log.debug("删除成功，id = {}", id);
            return Result.success("删除成功");
        }
        log.debug("删除失败，id = {}", id);
        return Result.failed("删除失败");
    }

    @Override
    public Result<String> updateResidentInfoById(Customer customer) {
        log.debug("更新客户信息，customer = {}", customer);
//        if(!checkIdNumber(createDto.getIdNumber())){
//            log.debug("身份证号不合法，createDto = {}", createDto);
//            return Result.failed("身份证号不合法");
//        }
        //查询居民是否存在
//        Customer resident = residentMapper.selectByPrimaryKey(createDto.getId());
//        if (resident == null) {
//            log.debug("居民不存在 id = {}", createDto.getId());
//            return Result.failed("居民不存在");
//        }
//        BeanUtils.copyProperties(createDto, resident);
        int result = residentMapper.updateByPrimaryKeySelective(customer);
        if (result > 0) {
            log.debug("更新成功，createDto = {}", customer);
            return Result.success("更新成功");
        }
        log.debug("更新失败，createDto = {}", customer);
        return Result.failed("更新失败");
    }

    @Override
    public Result<CustomerDto> getResidentInfoById(Integer id) {
        log.debug("获取客户信息，id = {}", id);
        Customer resident = residentMapper.selectByPrimaryKey(id);
        if (resident == null) {
            log.debug("未找到该客户，id = {}", id);
            return Result.failed("未找到该客户");
        }
        CustomerDto residentDto = convertToDto(resident);
        return Result.success(residentDto);
    }

    @Override
    public Result<String> addResident(Customer customer) {
        log.debug("添加客户信息，createDto = {}", customer);
//        if(!checkIdNumber(createDto.getIdNumber())){
//            log.debug("身份证号不合法，createDto = {}", customer);
//            return Result.failed("身份证号不合法");
//        }
//        Customer customer1 = new Customer();
//        BeanUtils.copyProperties(createDto, resident);
        int result = residentMapper.insertSelective(customer);
        if (result > 0) {
            log.debug("添加成功 {}", customer);
            return Result.success("添加成功");
        }
        log.debug("添加失败 {}", customer);
        return Result.failed("添加失败");
    }

    @Override
    public Result<List<CustomerDto>> list(String name,Integer page, Integer pageSize) {
        //检验参数合法
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        int offset = (page - 1) * pageSize;
        if (name != null) {
            name = "%" + name + "%";
        }
        List<Customer> residents = residentMapper.list(name,offset, pageSize);
        List<CustomerDto> dtos = convertToDto(residents);
        Integer total = residentMapper.countByExample(name);
        for (CustomerDto c:dtos) {
            c.setTotal(total);
        }
        return Result.success(dtos);
    }

    private List<CustomerDto> convertToDto(List<Customer> customers) {
        return customers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * 转换Resident为ResidentDto
     */
    private CustomerDto convertToDto(Customer resident) {
        CustomerDto residentDto = new CustomerDto();
        BeanUtils.copyProperties(resident, residentDto);
//        residentDto.setStreet(streetService.getById(resident.getStreetId()).getData().getName());
        return residentDto;
    }

    /**
     * 检验身份证合法性
     * @param idNumber 身份证号
     * @return true 合法 false 不合法
     */
    private boolean checkIdNumber(String idNumber){
        // 校验长度
        int length = idNumber.length();
        if(length != 15 && length != 18){
            return false;
        }
        // 校验组成
        char[] chars = idNumber.toCharArray();
        for(int i = 0; i < length - 1; i++){
            char c = chars[i];
            if(c < '0' || c > '9'){
                return false;
            }
        }
        // 校验出生日期
        String birthDay = "";
        if(length == 15){
            birthDay = "19" + idNumber.substring(6, 12);
        }else{
            birthDay = idNumber.substring(6, 14);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try{
            sdf.parse(birthDay);
        }catch(ParseException e){
            return false;
        }

        // 校验最后一位
        if(length == 18){
            char c = chars[17];
            if(c != 'X' && (c < '0' || c > '9')){
                return false;
            }
        }
        return true;
    }

}
