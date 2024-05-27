package com.sheepion.service.impl;

import com.sheepion.common.Result;
import com.sheepion.common.ResultCode;
import com.sheepion.dto.ApplicationInfoDto;
import com.sheepion.dto.ApproverInfoDto;
import com.sheepion.dto.UserInfoDto;
import com.sheepion.enums.ApplicationStatus;
import com.sheepion.enums.ApplicationType;
import com.sheepion.mapper.ApplicationMapper;
import com.sheepion.mapper.ApprovalLevelMapper;
import com.sheepion.mapper.ApproverMapper;
import com.sheepion.model.*;
import com.sheepion.service.ApplicationService;
import com.sheepion.service.PermissionService;
import com.sheepion.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private ApproverMapper approverMapper;
    @Autowired
    private ApprovalLevelMapper approvalLevelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public Result<Boolean> isRejected(Integer applicationId) {
        log.debug("检查申请是否已经被拒绝 applicationId={}", applicationId);
        ApproverExample approverExample = new ApproverExample();
        approverExample.createCriteria()
                .andApplicationIdEqualTo(applicationId)
                .andStatusEqualTo(ApplicationStatus.REJECT.getCode());
        return Result.success(approverMapper.countByExample(approverExample) > 0);
    }

    @Override
    public Result<Boolean> isApproved(Integer applicationId) {
        log.debug("检查申请是否已经被审批 applicationId={}", applicationId);
        Application application = applicationMapper.selectByPrimaryKey(applicationId);
        if(application == null){
            log.debug("获取申请信息失败 applicationId={}", applicationId);
            return Result.failed("获取申请信息失败");
        }
        return Result.success(Objects.equals(application.getStatus(), ApplicationStatus.PASS.getCode()));
    }

    @Override
    public Result<Long> getUnhandedCountByApplicationId(Integer applicationId) {
        log.debug("获取申请的未处理的审批数量 applicationId={}", applicationId);
        ApproverExample approverExample = new ApproverExample();
        approverExample.createCriteria()
                .andApplicationIdEqualTo(applicationId)
                .andStatusEqualTo(ApplicationStatus.PENDING.getCode());
        return Result.success(approverMapper.countByExample(approverExample));
    }

    @Override
    public Result<List<ApplicationInfoDto>> getSubmitted(Integer userId, ApplicationType type, Integer page, Integer pageSize) {
        log.debug("获取用户已提交的申请列表 userId={} type={} page={} pageSize={} ", userId,type, page, pageSize);
        //检查参数
        if(page == null || page < 1){
            page = 1;
        }
        if(pageSize == null || pageSize < 1){
            pageSize = 10;
        }
        int offset=(page - 1) * pageSize;
        List<Application> applications = applicationMapper.selectSubmittedByUserId(userId, type.getCode(), offset, pageSize);
        log.debug("已提交的申请列表 applications={}", applications);
        List<ApplicationInfoDto> applicationInfoDtos = applications.stream().map(this::convertToDto).collect(Collectors.toList());
        log.debug("已提交的申请dto列表 applicationInfoDtos={}", applicationInfoDtos);
        return Result.success(applicationInfoDtos);
    }

    @Override
    public Result<List<ApplicationInfoDto>> getUnhandedInfoByUserId(Integer userId, ApplicationType type, Integer page, Integer pageSize) {
        log.debug("获取用户未处理的申请列表 userId={} type={} page={} pageSize={} ", userId,type, page, pageSize);
        //检查参数
        if(page == null || page < 1){
            page = 1;
        }
        if(pageSize == null || pageSize < 1){
            pageSize = 10;
        }
        int offset=(page - 1) * pageSize;
        //获取未处理的审批
        List<Approver> approvers = approverMapper.selectUnhandledByUserId(userId, type.getCode(), offset, pageSize);
        log.debug("未处理的审批列表 approvers={}", approvers);
        //根据审批获取申请信息
        List<Integer> applicationIds = approvers.stream().map(Approver::getApplicationId).collect(Collectors.toList());
        ApplicationExample applicationExample = new ApplicationExample();
        applicationExample.createCriteria().andIdIn(applicationIds);
        List<Application> applications = applicationMapper.selectByExample(applicationExample);
        log.debug("申请信息列表 applications={}", applications);
        //生成dto
        List<ApplicationInfoDto> dtos=applications.stream().map(this::convertToDto).collect(Collectors.toList());
        log.debug("申请信息dto列表 dtos={}", dtos);
        return Result.success(dtos);
    }

    @Override
    public Result<Integer> countReceivedApplications(Integer userId, ApplicationType type) {
        log.debug("获取用户收到的申请数量 userId={} type={}", userId,type);
        return Result.success(approverMapper.countReceivedApprovers(userId, type.getCode()));
    }

    @Override
    public Result<List<ApplicationInfoDto>> getReceivedApplications(Integer userId, ApplicationType type, Integer page, Integer pageSize) {
        log.debug("获取用户收到的申请列表 userId={} type={} page={} pageSize={}", userId,type, page, pageSize);
        //验证参数
        if(page == null || page < 1){
            page = 1;
        }
        if(pageSize == null || pageSize < 1){
            pageSize = 10;
        }
        int offset=(page-1)*pageSize;
        //获取用户的审批
        List<Approver> approvers = approverMapper.selectReceivedApprovers(userId,type.getCode(),offset,pageSize);
        log.debug("用户的审批列表 approvers={}", approvers);
        //根据审批获取申请信息
        List<Integer> applicationIds = approvers.stream().map(Approver::getApplicationId).collect(Collectors.toList());
        ApplicationExample applicationExample = new ApplicationExample();
        applicationExample.createCriteria().andIdIn(applicationIds);
        List<Application> applications = applicationMapper.selectByExample(applicationExample);
        log.debug("申请信息列表 applications={}", applications);
        //生成dto
        List<ApplicationInfoDto> dtos=applications.stream().map(this::convertToDto).collect(Collectors.toList());
        log.debug("申请信息dto列表 dtos={}", dtos);
        return Result.success(dtos);
    }

    @Override
    @Transactional
    public Result<String> handle(Integer id, Integer userId, Integer status, String comment) {
        log.debug("处理申请 id={} userId={} status={} comment={}", id, userId, status, comment);
        //获取申请信息
        Application application = applicationMapper.selectByPrimaryKey(id);
        if (application == null) {
            log.debug("申请不存在 id={}", id);
            throw new IllegalArgumentException("申请不存在");
        }
        log.debug("申请信息 application={}", application);
        //获取下一个未处理的审批信息
        Approver approver = approverMapper.selectNextApprover(id);
        if (approver == null) {
            log.debug("下一个未处理审批不存在 id={}", id);
            throw new IllegalArgumentException("下一个未处理审批不存在");
        }
        log.debug("下一个未处理审批信息 approver={}", approver);
        //判断是否有权限审批
        Result<List<Permission>> permissionResult = permissionService.getPermissionsByUserId(userId);
        if (permissionResult.getCode() != ResultCode.SUCCESS.getCode()) {
            log.debug("获取用户权限失败 userId={}", userId);
            throw new RuntimeException("获取用户权限失败");
        }
        List<Permission> permissions = permissionResult.getData();
        boolean hasPermission = false;
        for (Permission permission : permissions) {
            if(permission.getId().equals(approver.getPermissionId())){
                hasPermission = true;
                break;
            }
        }
        if(!hasPermission){
            log.debug("用户没有权限处理申请 userId={} permissionId={}", userId, approver.getPermissionId());
            throw new RuntimeException("用户没有权限处理申请");
        }
        //更新审批信息
        approver.setUserId(userId);
        approver.setStatus(status);
        approver.setComment(comment);
        approver.setDate(Date.valueOf(LocalDate.now()));
        int result= approverMapper.updateByPrimaryKey(approver);
        if(result == 0){
            log.debug("审批信息更新失败 approver={}", approver);
            throw new RuntimeException("审批信息更新失败");
        }
        //如果审批被拒绝，修改申请状态
        if(Objects.equals(status, ApplicationStatus.REJECT.getCode())){
            application.setStatus(ApplicationStatus.REJECT.getCode());
            application.setReviewDate(Date.valueOf(LocalDate.now()));
            result = applicationMapper.updateByPrimaryKey(application);
            if(result == 0){
                log.debug("申请状态更新失败 application={}", application);
                throw new RuntimeException("申请状态更新失败");
            }
            return Result.success("审批信息更新成功");
        }
        //如果所有审批都已经通过，修改申请状态
        ApproverExample example= new ApproverExample();
        example.createCriteria()
                .andApplicationIdEqualTo(id)
                .andStatusNotEqualTo(ApplicationStatus.PASS.getCode());
        long count = approverMapper.countByExample(example);
        if(count==0){
            application.setStatus(ApplicationStatus.PASS.getCode());
            application.setReviewDate(Date.valueOf(LocalDate.now()));
            result = applicationMapper.updateByPrimaryKey(application);
            if(result == 0){
                log.warn("申请状态更新失败 application={}", application);
                throw new RuntimeException("申请状态更新失败");
            }
        }
        return Result.success("审批信息更新成功");
    }

    @Override
    public Result<ApplicationInfoDto> getInfoById(Integer id) {
        log.debug("通过id查询申请信息 id={}", id);
        Application application = applicationMapper.selectByPrimaryKey(id);
        if (application == null) {
            log.debug("申请不存在 id={}", id);
            return Result.failed("申请不存在");
        }
        ApplicationInfoDto dto = convertToDto(application);
        log.debug("申请信息查询成功 {}", dto);
        return Result.success(dto);
    }

    @Override
    @Transactional
    public Result<String> add(ApplicationType type, Integer userId,String reason, String payload) {
        log.debug("添加申请 type={} userId={}reason={} payload={}", type, userId, reason, payload);
        Application application = new Application();
        application.setUserId(userId);
        application.setReason(reason);
        //检查类型是否存在
        if(ApplicationType.getByCode(type.getCode()) == null){
            log.warn("申请类型不存在 type={}", type);
            throw new IllegalArgumentException("申请类型不存在");
        }
        application.setType(type.getCode());
        application.setStatus(ApplicationStatus.PENDING.getCode());
        application.setApplicationDate(Date.valueOf(LocalDate.now()));
        application.setPayload(payload);
        log.debug("添加申请信息 application={}", application);
        int result = applicationMapper.insert(application);
        if (result == 0) {
            log.warn("申请添加失败");
            throw new RuntimeException("申请添加失败");
        }
        log.debug("申请添加成功");
        //添加多级审批信息
        ApprovalLevelExample levelExample = new ApprovalLevelExample();
        levelExample.createCriteria().andTypeEqualTo(type.getType());
        List<ApprovalLevel> approvalLevels = approvalLevelMapper.selectByExample(levelExample);
        for(ApprovalLevel level : approvalLevels){
            Approver approver = convertToApprover(application.getId(), level);
            log.debug("添加多级审批信息 approver={}", approver);
            result = approverMapper.insert(approver);
            if (result == 0) {
                log.warn("多级审批信息添加失败");
                throw new RuntimeException("多级审批信息添加失败");
            }
        }
        return Result.success("申请添加成功");
    }
    private Approver convertToApprover(Integer applicationId, ApprovalLevel level){
        Approver model = new Approver();
        //根据权限名获取权限id
        Result<Permission> permissionResult = permissionService.getPermissionById(level.getPermissionId());
        if (permissionResult.getCode() != ResultCode.SUCCESS.getCode()) {
            log.warn("权限不存在 permission={}", level.getPermissionId());
            throw new RuntimeException("权限不存在");
        }
        model.setApplicationId(applicationId);
        model.setLevel(level.getLevel());
        model.setPermissionId(permissionResult.getData().getId());
        model.setStatus(ApplicationStatus.PENDING.getCode());
        return model;
    }

    /**
     * 转换申请信息为dto<br/>
     * 其中的多级审批信息将根据申请id查询所有的多级审批信息进行填充
     * @param model 申请信息
     * @return 完整的包含了所有信息的dto
     */
    private ApplicationInfoDto convertToDto(Application model) {
        log.debug("转换申请信息为dto model={}", model);
        //查询多级审批信息
        ApproverExample approverExample= new ApproverExample();
        approverExample.createCriteria().andApplicationIdEqualTo(model.getId());
        List<Approver> approvers = approverMapper.selectByExample(approverExample);
        log.debug("多级审批信息 approvers={}", approvers);
        ApplicationInfoDto dto = convertToDto(model, approvers);
        log.debug("申请信息转换成功 dto={}", dto);
        return dto;
    }
    private ApproverInfoDto convertToDto(Approver model){
        log.debug("转换审批信息为dto model={}", model);
        ApproverInfoDto dto = new ApproverInfoDto();
        dto.setId(model.getId());
        dto.setApplicationId(model.getApplicationId());
        dto.setLevel(model.getLevel());
        dto.setStatusId(model.getStatus());
        //防止空指针异常
        ApplicationStatus status = ApplicationStatus.getStatusByCode(model.getStatus());
        if (status == null) {
            log.debug("申请状态不存在 statusId={}", model.getStatus());
            dto.setStatus("未知状态");
        }else{
            dto.setStatus(status.getStatus());
        }
        //防止用户不存在
        dto.setUserId(model.getUserId());
        Result<UserInfoDto> userInfoDtoResult = userService.getAdministratorInfoById(model.getUserId());
        if(userInfoDtoResult.getCode() != ResultCode.SUCCESS.getCode()){
            log.debug("用户信息查询失败 userId={}", model.getUserId());
            dto.setUserName("未知用户");
        }else{
            dto.setUserName(userInfoDtoResult.getData().getName());
        }
        dto.setComment(model.getComment());
        dto.setDate(model.getDate());
        log.debug("审批信息转换成功 dto={}", dto);
        return dto;
    }

    private ApplicationInfoDto convertToDto(Application applicationModel, List<Approver> approverModel){
        log.debug("转换申请信息为dto applicationModel={} approverModel={}", applicationModel, approverModel);
        ApplicationInfoDto dto = new ApplicationInfoDto();
        dto.setId(applicationModel.getId());
        dto.setUserId(applicationModel.getUserId());
        Result<UserInfoDto> userInfoDtoResult = userService.getAdministratorInfoById(applicationModel.getUserId());
        if(userInfoDtoResult.getCode() != ResultCode.SUCCESS.getCode()){
            log.debug("用户信息查询失败 userId={}", applicationModel.getUserId());
            dto.setUserName("未知用户");
        }else{
            dto.setUserName(userInfoDtoResult.getData().getName());
        }
        dto.setReason(applicationModel.getReason());
        dto.setDate(applicationModel.getApplicationDate());
        dto.setStatusId(applicationModel.getStatus());
        ApplicationStatus status = ApplicationStatus.getStatusByCode(applicationModel.getStatus());
        if (status == null) {
            log.debug("申请状态不存在 statusId={}", applicationModel.getStatus());
            dto.setStatus("未知状态");
        }else{
            dto.setStatus(status.getStatus());
        }
        //填充参数提供的多级审批信息
        dto.setApprovers(approverModel.stream().map(this::convertToDto).collect(Collectors.toList()));
        dto.setPayload(applicationModel.getPayload());
        log.debug("申请信息转换成功 dto={}", dto);
        return dto;
    }
}
