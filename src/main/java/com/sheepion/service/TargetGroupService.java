package com.sheepion.service;

import com.sheepion.common.Result;
import com.sheepion.dto.*;
import com.sheepion.model.Application;

import java.util.List;

public interface TargetGroupService {

    /**
     * 通过id查询重点人群信息
     * @param id 重点人群id
     * @return 重点人群信息
     */
    Result<TargetGroupDto> getInfoById(Integer id);

    /**
     * 分页查询重点人群列表
     * @param page 页码
     * @param pageSize 每页数量
     * @return 重点人群列表
     */
    Result<List<TargetGroupDto>> list(Integer page,Integer pageSize);

    /**
     * <p>获取用户有权处理的申请的列表，包含申请的所有信息</p>
     * <p>
     *    满足以下条件才会被返回：<br/>
     *    1. 上一级审批已经通过<br/>
     *    2. 用户具有对应的审批权限<br/>
     * </p>
     * @param userId 用户id
     * @param page 页码
     * @param pageSize 每页数量
     * @return 申请列表
     */
    Result<List<ApplicationInfoDto>> listApplication(Integer userId,Integer page,Integer pageSize);

    /**
     * 添加重点人群
     * @param targetGroupCreateDto 重点人群参数
     * @return 结果信息
     */
    Result<String> add(TargetGroupCreateDto targetGroupCreateDto);

    /**
     * 通过id修改重点人群信息
     * @param targetGroupDto 新的信息
     * @return 结果消息
     */
    Result<String> updateById(TargetGroupDto targetGroupDto);

    /**
     * 通过id删除重点人群信息
     * @param id 重点人员id
     * @return 结果消息
     */
    Result<String> deleteById(Integer id);

    /**
     * 处理审批重点人员添加申请
     * @param applicationApproveDto 审批参数
     * @return 结果消息
     */
    Result<String> approve(ApplicationApproveDto applicationApproveDto);

    /**
     * 查询申请的状态，会返回申请的所有信息<br/>
     * 只能查看自己发起的申请
     * @param id 申请id
     * @return 申请信息
     */
    Result<ApplicationInfoDto> status(Integer id);

    Result<Integer> count();

    Result<List<TargetGroupDto>> getAll();

    Result<List<CategoryDto>> getCategories();

    /**
     * 获取用户发起的申请的列表
     * @param userId 用户id
     * @param page 页码
     * @param pageSize 每页数量
     * @return 申请列表
     */

    Result<List<ApplicationInfoDto>> listSubmitted(Integer userId, Integer page, Integer pageSize);

    /**
     * 获取有权处理的申请的个数
     * @param userId 用户id
     * @return 个数
     */
    Result<Integer> countApplication(Integer userId);
}
