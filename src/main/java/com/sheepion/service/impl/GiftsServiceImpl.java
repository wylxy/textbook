package com.sheepion.service.impl;

import com.sheepion.common.Result;
import com.sheepion.dto.CustomerDto;
import com.sheepion.mapper.GiftsMapper;
import com.sheepion.model.Customer;
import com.sheepion.model.Gifts;
import com.sheepion.model.Log;
import com.sheepion.model.ResidentExample;
import com.sheepion.service.GiftsService;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class GiftsServiceImpl implements GiftsService {
    @Autowired
    private GiftsMapper giftsMapper;

    @Override
    public Result<List<Gifts>> getAll(String name) {
        log.debug("获取所有赠品信息");
        ResidentExample example = new ResidentExample();
        if (name != null) {
            example.createCriteria().andNameLike("%" + name + "%");
        }
        List<Gifts> gifts = giftsMapper.selectByExample(example);
        if (gifts == null) {
            return Result.failed("获取赠品信息失败");
        }
//        List<CustomerDto> dtos=residents.stream().map(this::convertToDto).collect(Collectors.toList());
        return Result.success(gifts);
    }

    @Override
    public Result<String> deleteResident(Integer id) {
        log.debug("删除赠品信息，id = {}", id);
        //获取当前登录的用户信息
//        Result<UserInfoDto> userInfoDtoResult = userService.getAdministratorInfoById(UserHolder.getCurrentUser());
//        if (userInfoDtoResult.getCode() != ResultCode.SUCCESS.getCode()) {
//            log.debug("获取赠品信息失败");
//            return Result.failed("获取赠品信息失败");
//        }
//        UserInfoDto administrator = userInfoDtoResult.getData();
//        log.debug("当前登录用户信息：{}", administrator);
        //查询赠品是否存在
//        Customer resident = giftsMapper.selectByPrimaryKey(id);
//        if (resident == null) {
//            log.debug("赠品不存在 id = {}", id);
//            return Result.failed("赠品不存在");
//        }
//        log.debug("赠品信息：{}", resident);
        //如果当前登录的用户是街道工作人员，只能删除自己街道的赠品
//        if (administrator.getRoleId() == Role.STREET_STAFF.getCode()) {
//            if(!Objects.equals(resident.getStreetId(), administrator.getStreetId())){
//                log.debug("无权限删除该赠品 id = {}", id);
//                return Result.failed("无权限删除该赠品");
//            }
//        }
        //删除赠品
        int result = giftsMapper.deleteByPrimaryKey(id);
        if (result > 0) {
            log.debug("删除成功，id = {}", id);
            return Result.success("删除成功");
        }
        log.debug("删除失败，id = {}", id);
        return Result.failed("删除失败");
    }

    @Override
    public Result<String> updateResidentInfoById(Gifts gifts) {
        log.debug("更新赠品信息，customer = {}", gifts);
//        if(!checkIdNumber(createDto.getIdNumber())){
//            log.debug("身份证号不合法，createDto = {}", createDto);
//            return Result.failed("身份证号不合法");
//        }
        //查询赠品是否存在
//        Customer resident = giftsMapper.selectByPrimaryKey(createDto.getId());
//        if (resident == null) {
//            log.debug("赠品不存在 id = {}", createDto.getId());
//            return Result.failed("赠品不存在");
//        }
//        BeanUtils.copyProperties(createDto, resident);
        int result = giftsMapper.updateByPrimaryKeySelective(gifts);
        if (result > 0) {
            log.debug("更新成功，createDto = {}", gifts);
            return Result.success("更新成功");
        }
        log.debug("更新失败，createDto = {}", gifts);
        return Result.failed("更新失败");
    }

    @Override
    public Result<Gifts> getResidentInfoById(Integer id) {
        log.debug("获取赠品信息，id = {}", id);
        Gifts gifts = giftsMapper.selectByPrimaryKey(id);
        if (gifts == null) {
            log.debug("未找到该赠品，id = {}", id);
            return Result.failed("未找到该赠品");
        }
//        CustomerDto residentDto = convertToDto(resident);
        return Result.success(gifts);
    }

    @Override
    public Result<String> addResident(Gifts gifts) {
        log.debug("添加赠品信息，gifts = {}", gifts);
//        if(!checkIdNumber(createDto.getIdNumber())){
//            log.debug("身份证号不合法，createDto = {}", customer);
//            return Result.failed("身份证号不合法");
//        }
//        Customer customer1 = new Customer();
//        BeanUtils.copyProperties(createDto, resident);
        int result = giftsMapper.insertSelective(gifts);
        if (result > 0) {
            log.debug("添加成功 {}", gifts);
            return Result.success("添加成功");
        }
        log.debug("添加失败 {}", gifts);
        return Result.failed("添加失败");
    }

    @Override
    public Result<List<Gifts>> list(String name,Integer page, Integer pageSize) {
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
        List<Gifts> gifts = giftsMapper.list(name,offset, pageSize);
//        List<CustomerDto> dtos = convertToDto(residents);
//        Integer total = giftsMapper.countByExample(name);
//        for (CustomerDto c:dtos) {
//            c.setTotal(total);
//        }
        return Result.success(gifts);
    }
    @Override
    public Result<List<Log>> logs(Integer page, Integer pageSize) {
        //检验参数合法
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        int offset = (page - 1) * pageSize;

        List<Log> logs = giftsMapper.logs(offset, pageSize);
//        List<CustomerDto> dtos = convertToDto(residents);
//        Integer total = giftsMapper.countByExample(name);
//        for (CustomerDto c:dtos) {
//            c.setTotal(total);
//        }
        return Result.success(logs);
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
