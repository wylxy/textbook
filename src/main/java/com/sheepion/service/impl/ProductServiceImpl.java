package com.sheepion.service.impl;

import com.sheepion.common.Result;
import com.sheepion.dto.CustomerDto;
import com.sheepion.mapper.GiftsMapper;
import com.sheepion.mapper.ProductMapper;
import com.sheepion.model.*;
import com.sheepion.service.GiftsService;
import com.sheepion.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//    @Override
//    public Result<List<P>> getAll(String name) {
//        log.debug("获取所有订单信息");
//        ResidentExample example = new ResidentExample();
//        if (name != null) {
//            example.createCriteria().andNameLike("%" + name + "%");
//        }
//        List<Gifts> gifts = giftsMapper.selectByExample(example);
//        if (gifts == null) {
//            return Result.failed("获取订单信息失败");
//        }
////        List<CustomerDto> dtos=residents.stream().map(this::convertToDto).collect(Collectors.toList());
//        return Result.success(gifts);
//    }
//
//    @Override
//    public Result<String> deleteResident(Integer id) {
//        log.debug("删除订单信息，id = {}", id);
//        //获取当前登录的用户信息
////        Result<UserInfoDto> userInfoDtoResult = userService.getAdministratorInfoById(UserHolder.getCurrentUser());
////        if (userInfoDtoResult.getCode() != ResultCode.SUCCESS.getCode()) {
////            log.debug("获取订单信息失败");
////            return Result.failed("获取订单信息失败");
////        }
////        UserInfoDto administrator = userInfoDtoResult.getData();
////        log.debug("当前登录用户信息：{}", administrator);
//        //查询订单是否存在
////        Customer resident = giftsMapper.selectByPrimaryKey(id);
////        if (resident == null) {
////            log.debug("订单不存在 id = {}", id);
////            return Result.failed("订单不存在");
////        }
////        log.debug("订单信息：{}", resident);
//        //如果当前登录的用户是街道工作人员，只能删除自己街道的订单
////        if (administrator.getRoleId() == Role.STREET_STAFF.getCode()) {
////            if(!Objects.equals(resident.getStreetId(), administrator.getStreetId())){
////                log.debug("无权限删除该订单 id = {}", id);
////                return Result.failed("无权限删除该订单");
////            }
////        }
//        //删除订单
//        int result = giftsMapper.deleteByPrimaryKey(id);
//        if (result > 0) {
//            log.debug("删除成功，id = {}", id);
//            return Result.success("删除成功");
//        }
//        log.debug("删除失败，id = {}", id);
//        return Result.failed("删除失败");
//    }
//
    @Override
    public Result<String> updateProductInfoById(Product product) {
        log.debug("更新订单信息，product = {}", product);
        int result = productMapper.updateById(product);
        if (result > 0) {
            log.debug("更新成功，createDto = {}", product);
            return Result.success("更新成功");
        }
        log.debug("更新失败，createDto = {}", product);
        return Result.failed("更新失败");
    }

    @Override
    public Result<Order> getInfoById(Integer id) {
        log.debug("获取订单信息，id = {}", id);
        Order order = productMapper.selectByPrimaryKey(id);
        if (order == null) {
            log.debug("未找到该订单，id = {}", id);
            return Result.failed("未找到该订单");
        }
//        CustomerDto residentDto = convertToDto(resident);
        return Result.success(order);
    }

    @Override
    public Result<String> add(Order order) {
        log.debug("添加订单信息，order = {}", order);
//        if(!checkIdNumber(createDto.getIdNumber())){
//            log.debug("身份证号不合法，createDto = {}", customer);
//            return Result.failed("身份证号不合法");
//        }
//        Customer customer1 = new Customer();
//        BeanUtils.copyProperties(createDto, resident);
        order.setGiftDate(sdf.format(new Date()));
        int result = productMapper.insert(order);
        if (result > 0) {
            log.debug("添加成功 {}", order);
            return Result.success("添加成功");
        }
        log.debug("添加失败 {}", order);
        return Result.failed("添加失败");
    }

    @Override
    public Result<List<Product>> list(String name,Integer page, Integer pageSize) {
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
        List<Product> gifts = productMapper.list(name,offset, pageSize);
//        List<CustomerDto> dtos = convertToDto(residents);
//        Integer total = giftsMapper.countByExample(name);
//        for (CustomerDto c:dtos) {
//            c.setTotal(total);
//        }
        return Result.success(gifts);
    }
    @Override
    public Result<List<Product>> listEvaluate(String name,Integer page, Integer pageSize) {
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
        List<Product> gifts = productMapper.listEvaluate(name,offset, pageSize);
//        List<CustomerDto> dtos = convertToDto(residents);
//        Integer total = giftsMapper.countByExample(name);
//        for (CustomerDto c:dtos) {
//            c.setTotal(total);
//        }
        return Result.success(gifts);
    }
    @Override
    public Result<List<Product>> listEvaluateYear(String name,Integer page, Integer pageSize) {
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
        List<Product> gifts = productMapper.listEvaluateYear(name,offset, pageSize);
//        List<CustomerDto> dtos = convertToDto(residents);
//        Integer total = giftsMapper.countByExample(name);
//        for (CustomerDto c:dtos) {
//            c.setTotal(total);
//        }
        return Result.success(gifts);
    }
}
