package com.sheepion.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheepion.common.Result;
import com.sheepion.common.ResultCode;
import com.sheepion.common.UserHolder;
import com.sheepion.dto.*;
import com.sheepion.enums.ApplicationStatus;
import com.sheepion.enums.ApplicationType;
import com.sheepion.enums.Category;
import com.sheepion.enums.Role;
import com.sheepion.mapper.ApplicationMapper;
import com.sheepion.mapper.TargetGroupMapper;
import com.sheepion.model.Application;
import com.sheepion.model.TargetGroup;
import com.sheepion.model.TargetGroupExample;
import com.sheepion.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TargetGroupServiceImpl implements TargetGroupService {
    @Autowired
    private TargetGroupMapper targetGroupMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ResidentService residentService;
    @Autowired
    private StreetService streetService;
    @Autowired
    private ApplicationService applicationService;


    @Override
    public Result<List<ApplicationInfoDto>> listSubmitted(Integer userId, Integer page, Integer pageSize) {
        log.debug("获取用户发起的申请的列表 userId:{} page:{} pageSize:{}", userId, page, pageSize);
        //验证参数
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        return applicationService.getSubmitted(userId, ApplicationType.TARGET_GROUP, page, pageSize);
    }

    @Override
    public Result<Integer> countApplication(Integer userId) {
        return applicationService.countReceivedApplications(userId, ApplicationType.TARGET_GROUP);
    }

    @Override
    public Result<List<ApplicationInfoDto>> listApplication(Integer userId, Integer page, Integer pageSize) {
        log.debug("获取用户有权处理的申请的列表，包含申请的所有信息 userId:{}", userId);
        //验证参数
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        return applicationService.getReceivedApplications(userId, ApplicationType.TARGET_GROUP, page, pageSize);
    }

    @Override
    public Result<Integer> count() {
        int result = (int) targetGroupMapper.countByExample(null);
        return Result.success(result);
    }

    @Override
    public Result<List<TargetGroupDto>> getAll() {
        log.debug("获取所有重点人群信息");
        TargetGroupExample example = new TargetGroupExample();
        List<TargetGroup> targetGroups = targetGroupMapper.selectByExample(example);
        if (targetGroups == null) {
            return Result.failed("获取重点人群信息失败");
        }
        List<TargetGroupDto> dtos = targetGroups.stream().map(this::convertToDto).collect(Collectors.toList());
        return Result.success(dtos);
    }

    @Override
    public Result<String> add(TargetGroupCreateDto targetGroupCreateDto) {
        log.debug("申请添加重点人群, {}", targetGroupCreateDto);
        try {
            Result<String> addResult = applicationService.add(ApplicationType.TARGET_GROUP,
                    UserHolder.getCurrentUser(),
                    targetGroupCreateDto.getReason(),
                    objectMapper.writeValueAsString(targetGroupCreateDto)
            );
            log.debug("发起申请结果 {}", addResult);
            if (addResult.getCode() != ResultCode.SUCCESS.getCode()) {
                log.debug("发起申请失败");
                return Result.failed("发起申请失败 " + addResult.getMessage());
            }
            return Result.success("发起申请成功");
        } catch (JsonProcessingException e) {
            log.debug("重点人群申请信息转换失败");
            e.printStackTrace();
            throw new RuntimeException("重点人群申请信息转换失败", e);
        }
    }

    @Override
    @Transactional
    public Result<String> approve(ApplicationApproveDto applicationApproveDto) {
        log.debug("处理审批重点人员添加申请 {}", applicationApproveDto);
        //检查参数是否合法
        if (applicationApproveDto == null) {
            log.debug("参数不能为空");
            throw new IllegalArgumentException("参数不能为空");
        }
        Integer applicationId = applicationApproveDto.getApplicationId();
        Integer statusId = applicationApproveDto.getStatusId();
        if (applicationId == null) {
            log.debug("申请id不能为空");
            throw new IllegalArgumentException("申请id不能为空");
        } else if (statusId == null) {
            log.debug("审批状态不能为空");
            throw new IllegalArgumentException("审批状态不能为空");
        } else if (ApplicationStatus.getStatusByCode(statusId) == null) {
            log.debug("审批状态不存在");
            throw new IllegalArgumentException("审批状态不存在");
        }
        //尝试处理
        Result<String> handleResult = applicationService.handle(
                applicationApproveDto.getApplicationId(),
                UserHolder.getCurrentUser(),
                applicationApproveDto.getStatusId(),
                applicationApproveDto.getComment());
        log.debug("处理申请结果 {}", handleResult);
        //没有权限的情况
        if (handleResult.getCode() == ResultCode.FORBIDDEN.getCode()) {
            log.debug("当前用户无权处理该申请");
            throw new RuntimeException("当前用户无权处理该申请");
        }
        //其他处理失败的情况
        if (handleResult.getCode() != ResultCode.SUCCESS.getCode()) {
            log.debug("处理申请失败");
            throw new RuntimeException("处理申请失败 " + handleResult.getMessage());
        }
        log.debug("处理申请成功");
        //如果申请已经被同意，则添加重点人群
        Result<Boolean> approvedResult = applicationService.isApproved(applicationId);
        log.debug("申请是否已经被同意 {}", approvedResult);
        if (approvedResult.getCode() != ResultCode.SUCCESS.getCode()) {
            log.debug("查询申请状态失败");
            throw new RuntimeException("查询申请状态失败 " + approvedResult.getMessage());
        }
        if (approvedResult.getData()) {
            log.debug("申请已经被同意，添加重点人群");
            try {
                //获取申请信息，从中获取payload，转换为model，添加到数据库
                Result<ApplicationInfoDto> result = applicationService.getInfoById(applicationId);
                if (result.getCode() != ResultCode.SUCCESS.getCode()) {
                    log.debug("查询申请信息失败");
                    return Result.failed("查询申请信息失败 " + result.getMessage());
                }
                ApplicationInfoDto application = result.getData();
                TargetGroupCreateDto targetGroupCreateDto = objectMapper.readValue(application.getPayload(), TargetGroupCreateDto.class);
                TargetGroup targetGroup = convertToModel(targetGroupCreateDto);
                int i = targetGroupMapper.insertSelective(targetGroup);
                if (i == 0) {
                    log.debug("添加重点人群失败");
                    throw new RuntimeException("添加重点人群失败");
                }
                log.debug("添加重点人群成功");
            } catch (IOException e) {
                log.debug("重点人群申请信息转换失败");
                throw new RuntimeException("重点人群申请信息转换失败", e);
            }
        }
        return Result.success("处理成功");
    }

    @Override
    public Result<TargetGroupDto> getInfoById(Integer id) {
        log.debug("查询目标群体信息 id: {}", id);
        TargetGroup targetGroup = targetGroupMapper.selectByPrimaryKey(id);
        if (targetGroup == null) {
            log.debug("查询失败");
            return Result.failed("查询失败");
        }
        TargetGroupDto dto = convertToDto(targetGroup);
        log.debug("查询成功, {}", dto);
        return Result.success(dto);
    }

    @Override
    public Result<List<TargetGroupDto>> list(Integer page, Integer pageSize) {
        log.debug("分页查询目标群体列表 page: {}, pageSize: {}", page, pageSize);
        //检验参数合法
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        int offset = (page - 1) * pageSize;
        List<TargetGroup> groups = targetGroupMapper.selectByPage(offset, pageSize);
        if (groups == null) {
            log.warn("查询失败");
            return Result.failed("查询失败");
        }
        List<TargetGroupDto> dtos = groups.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return Result.success(dtos);
    }

    @Override
    public Result<List<CategoryDto>> getCategories() {
        log.debug("查询重点人群类别列表");
        List<CategoryDto> categories = new ArrayList<>(Arrays.asList(Category.values())).stream()
                .map(CategoryDto::new).collect(Collectors.toList());
        return Result.success(categories);
    }

    @Override
    public Result<String> updateById(TargetGroupDto targetGroupDto) {
        log.debug("修改重点人员信息 {}", targetGroupDto);
        //检查重点人员是否存在
        TargetGroup targetGroup = targetGroupMapper.selectByPrimaryKey(targetGroupDto.getId());
        if (targetGroup == null) {
            log.warn("重点人员不存在 id: {}", targetGroupDto.getId());
            return Result.failed("重点人员不存在");
        }
        //保存到数据库
        targetGroup = convertToModel(targetGroupDto);
        int result = targetGroupMapper.updateByPrimaryKeySelective(targetGroup);
        if (result > 0) {
            log.debug("修改成功 {}", targetGroup);
            return Result.success("修改成功");
        }
        log.debug("修改失败 {}", targetGroup);
        return Result.failed("修改失败");
    }

    @Override
    public Result<String> deleteById(Integer id) {
        log.debug("删除重点人员 id: {}", id);
        int result = targetGroupMapper.deleteByPrimaryKey(id);
        if (result > 0) {
            log.debug("删除成功");
            return Result.success("删除成功");
        }
        return Result.failed("删除失败");
    }

    @Override
    public Result<ApplicationInfoDto> status(Integer id) {
        log.debug("查询申请状态 id: {}", id);
        Result<ApplicationInfoDto> result = applicationService.getInfoById(id);
        log.debug("查询结果 {}", result);
        //如果查询成功，判断一下这个申请是不是当前用户添加的，如果不是，不允许查询
        if (result.getCode() == ResultCode.SUCCESS.getCode()) {
            if (!Objects.equals(result.getData().getUserId(), UserHolder.getCurrentUser())) {
                log.debug("当前用户无权查询该申请");
                return Result.failed(ResultCode.FORBIDDEN, "当前用户无权查询该申请");
            }
        }
        return result;
    }


    private TargetGroup convertToModel(TargetGroupDto dto) {
        TargetGroup model = new TargetGroup();
        model.setId(dto.getId());
        model.setCategoryId(dto.getCategoryId());
        model.setStreetId(dto.getStreetId());
        model.setResidentId(model.getResidentId());
        model.setResponsiblePerson(dto.getResponsiblePersonId());
        return model;
    }

    private TargetGroup convertToModel(TargetGroupCreateDto dto) {
        TargetGroup model = new TargetGroup();
        model.setResidentId(dto.getResidentId());
        model.setResponsiblePerson(dto.getResponsiblePersonId());
        model.setStreetId(dto.getStreetId());
        model.setCategoryId(dto.getCategoryId());
        return model;
    }

    private TargetGroupDto convertToDto(TargetGroup targetGroup) {
        TargetGroupDto dto = new TargetGroupDto();
        //负责人信息
        UserInfoDto user = userService.getAdministratorInfoById(targetGroup.getResponsiblePerson()).getData();
        //居民信息
        CustomerDto resident = residentService.getResidentInfoById(targetGroup.getResidentId()).getData();
        //如果居民或者负责人不存在，忽视这条记录
        if (user == null || resident == null) {
            log.warn("负责人或者居民不存在");
            return null;
        }
        //给dto赋值
        dto.setId(targetGroup.getId());
//        dto.setName(resident.getName());
        dto.setCategoryId(targetGroup.getCategoryId());
        log.debug("分类id {}", targetGroup.getCategoryId());
        dto.setCategory(Category.getCategoryNameById(targetGroup.getCategoryId()));
        log.debug("分类名称 {}", dto.getCategory());
//        dto.setContactInfo(resident.getContactInfo());
//        dto.setStreetId(resident.getStreetId());
//        dto.setStreet(resident.getStreet());
        dto.setResponsiblePersonId(user.getId());
        dto.setResponsiblePerson(user.getName());
        return dto;
    }
}
