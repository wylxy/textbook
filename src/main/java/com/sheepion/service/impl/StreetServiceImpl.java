package com.sheepion.service.impl;

import com.sheepion.common.Result;
import com.sheepion.common.ResultCode;
import com.sheepion.common.UserHolder;
import com.sheepion.dto.StreetCreateDto;
import com.sheepion.dto.StreetDto;
import com.sheepion.dto.UserInfoDto;
import com.sheepion.enums.Role;
import com.sheepion.mapper.StreetMapper;
import com.sheepion.model.GiftType;
import com.sheepion.model.StreetExample;
import com.sheepion.service.StreetService;
import com.sheepion.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StreetServiceImpl implements StreetService {

    @Autowired
    private UserService userService;
    @Autowired
    private StreetMapper streetMapper;

    @Override
    public Result<List<GiftType>> getAll(String name) {
        log.debug("获取所有街道信息");
        StreetExample example = new StreetExample();
        if (name != null) {
           name = "%" + name + "%";
        }
        List<GiftType> giftTypes = streetMapper.selectByExample(name);
        if (giftTypes == null) {
            return Result.failed("获取赠品类别失败");
        }
//        List<StreetDto> dtos = streets.stream().map(this::convertToDto).collect(Collectors.toList());
        return Result.success(giftTypes);
    }

    @Override
    public Result<Integer> add(GiftType giftType) {
        log.debug("添加赠品类别, {}", giftType);
        int result = streetMapper.insertSelective(giftType);
        if (result > 0) {
            log.debug("添加赠品类别成功, {}", giftType);
            return Result.success(giftType.getGiftTypeId());
        }
        log.debug("添加赠品类别失败, {}", giftType);
        return Result.failed("添加赠品类别失败");
    }
//
//    @Override
//    public Result<Integer> count() {
//        log.debug("统计街道数量");
//        StreetExample example = new StreetExample();
//        int result = (int) streetMapper.countByExample(example);
//        log.debug("街道数量：{}", result);
//        return Result.success(result);
//    }
//
    @Override
    public Result<List<GiftType>> list(Integer page, Integer pageSize) {
        log.debug("分页查询赠品类别列表");
        //检验参数
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        int offset = (page - 1) * pageSize;
        List<GiftType> giftTypes = streetMapper.selectByPage(offset, pageSize);
//        List<StreetDto> dtos = convertToDto(streets);
        log.debug("赠品类别列表：{}", giftTypes);
        return Result.success(giftTypes);
    }
//
    @Override
    public Result<GiftType> getById(Integer id) {
        log.debug("通过id查询赠品类别信息, {}", id);
        GiftType giftType = streetMapper.selectByPrimaryKey(id);
        if (giftType == null) {
            log.debug("赠品类别不存在");
            return Result.failed("赠品类别不存在");
        }
//        StreetDto dto = convertToDto(street);
        log.debug("赠品类别信息：{}", giftType);
        return Result.success(giftType);
    }
//
    @Override
    public Result<String> updateById(GiftType giftType) {
        log.debug("通过id更新赠品类别信息, {}", giftType);
//        //如果是街道管理员，只能修改自己的街道信息
//        Result<UserInfoDto> userInfoDtoResult = userService.getAdministratorInfoById(UserHolder.getCurrentUser());
//        if (userInfoDtoResult.getCode() != ResultCode.SUCCESS.getCode()) {
//            log.debug("获取用户信息失败");
//            return Result.failed("获取用户信息失败");
//        }
//        UserInfoDto userInfoDto = userInfoDtoResult.getData();
//        if (userInfoDto.getRoleId() == Role.STREET_STAFF.getCode()
//                && !userInfoDto.getStreetId().equals(dto.getId())) {
//            log.debug("街道管理员只能修改自己的街道信息");
//            return Result.failed("街道管理员只能修改自己的街道信息");
//        }
        //更新街道信息
        int result = streetMapper.updateByPrimaryKeySelective(giftType);
        if (result > 0) {
            log.debug("更新赠品类别成功, {}", giftType);
            return Result.success("更新赠品类别成功");
        }
        log.debug("更新赠品类别失败, {}", giftType);
        return Result.failed("更新赠品类别失败");
    }
//
    @Override
    public Result<String> deleteById(Integer id) {
        log.debug("通过id删除赠品类别, {}", id);
        //检查街道是否存在
//        GiftType giftType = streetMapper.selectByPrimaryKey(id);
//        if (street == null) {
//            log.debug("街道不存在");
//            return Result.failed("街道不存在");
//        }
        //检查用户是否有权限删除，街道工作人员无权限
//        Result<UserInfoDto> userInfoDtoResult = userService.getAdministratorInfoById(UserHolder.getCurrentUser());
//        if (userInfoDtoResult.getCode() != ResultCode.SUCCESS.getCode()) {
//            log.debug("获取用户信息失败");
//            return Result.failed("获取用户信息失败");
//        }
//        UserInfoDto userInfoDto = userInfoDtoResult.getData();
//        if (userInfoDto.getRoleId() == Role.STREET_STAFF.getCode()) {
//            log.debug("街道工作人员无权限删除街道");
//            return Result.failed("街道工作人员无权限删除街道");
//        }
        //删除街道
        int result = streetMapper.deleteByPrimaryKey(id);
        if (result > 0) {
            log.debug("删除赠品类别成功, {}", id);
            return Result.success("删除赠品类别成功");
        }
        log.debug("删除赠品类别失败, {}", id);
        return Result.failed("删除赠品类别失败");
    }
//
//    private List<StreetDto> convertToDto(List<Street> streets) {
//        return streets.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    /**
//     * 转换street为streetDto
//     */
//    private StreetDto convertToDto(Street street) {
//        StreetDto streetDto = new StreetDto();
//        streetDto.setId(street.getId());
//        streetDto.setName(street.getName());
//        streetDto.setDescription(street.getDescription());
//        return streetDto;
//    }
//
//    /**
//     * 转换streetDto为street
//     */
//    private Street convertToModel(StreetDto streetDto) {
//        Street street = new Street();
//        street.setId(streetDto.getId());
//        street.setName(streetDto.getName());
//        street.setDescription(streetDto.getDescription());
//        return street;
//    }
//
//    /**
//     * 转换streetCreateDto为street
//     */
//    private Street convertToModel(StreetCreateDto streetCreateDto) {
//        Street street = new Street();
//        street.setName(streetCreateDto.getName());
//        street.setDescription(streetCreateDto.getDescription());
//        return street;
//    }
}
