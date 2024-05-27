package com.sheepion.service;

import com.sheepion.common.Result;
import com.sheepion.dto.ApplicationInfoDto;
import com.sheepion.enums.ApplicationType;

import java.util.List;

/**
 * 使用通用的Application，方便在编码新的业务模块的时候快速创建一个流程审批
 * <p>
 * 根据使用情况的不同，可以设置多级审批，每一级对应一个审批权限，所有具备审批权限的人都可以审批
 * <p>
 * 业务流程：在代码里规定审批的流程，比如说在代码里编写低保的申请流程，这个流程需要两级审批，第一级需要application.handle.1权限<br/>
 * 第二级需要application.handle.2权限，这样的话只要有权限的人就能来进行审批。并且需要第一级审批完了第二级才能进行审批
 * <p>
 *     每一个申请可以存一个payload，这个payload对于外部不可见，可以存一些业务相关的信息，
 *     比如说添加重点人员的时候，把重点人员的添加参数以json格式保存进去，在所有的审批都被同意以后，
 *     从payload里面读取参数，生成model，然后保存到数据库
 * </p>
 */
public interface ApplicationService {
    /**
     * 发送一个审批申请
     *
     * @param type      审批类型
     * @param userId    申请人id
     * @param reason    申请理由
     * @param payload   申请内容，对外部不可见，建议使用json格式以便通过请求的时候插入到数据库中使用
     * @return 结果消息
     */
    Result<String> add(ApplicationType type,
                       Integer userId,
                       String reason,
                       String payload);

    /**
     * 通过id查询申请信息
     *
     * @param id 申请id
     * @return 申请信息
     */
    Result<ApplicationInfoDto> getInfoById(Integer id);

    /**
     * 获取用户的未处理的申请列表。已经处理的、前一级未通过的申请不会列出
     * <p>
     * 同时满足以下要求才会被列出：<br/>
     * 1. 审批未被处理<br/>
     * 2. 前一级的审批已经通过<br/>
     * 3. 用户具有对应的审批权限<br/>
     * </p>
     *
     * @param userId 用户id
     * @param type     申请类型
     * @param page     页码
     * @param pageSize 每页数量
     * @return 申请列表
     */
    Result<List<ApplicationInfoDto>> getUnhandedInfoByUserId(Integer userId,
                                                             ApplicationType type,
                                                             Integer page,
                                                             Integer pageSize);

    /**
     * 统计用户收到的所有申请的数量
     * @param userId 用户id
     * @param type 申请类型
     * @return 申请数量
     */
    Result<Integer> countReceivedApplications(Integer userId,ApplicationType type);

    /**
     * <p>获取用户收到的所有的申请列表，包含已经处理的</p>
     * <p>
     * 同时满足以下要求才会被列出：<br/>
     * 1. 前一级的审批已经通过<br/>
     * 2. 用户具有对应的审批权限<br/>
     * </p>
     *
     * @param userId   用户id
     * @param type     申请类型
     * @param page     页码
     * @param pageSize 每页数量
     * @return 申请列表
     */
    Result<List<ApplicationInfoDto>> getReceivedApplications(Integer userId,
                                                             ApplicationType type,
                                                             Integer page,
                                                             Integer pageSize);

    /**
     * 获取用户提交的申请列表
     * @param userId 用户id
     * @param type 申请类型
     * @param page 页码
     * @param pageSize 每页数量
     * @return 申请列表
     */
    Result<List<ApplicationInfoDto>> getSubmitted(Integer userId,
                                                  ApplicationType type,
                                                  Integer page,
                                                  Integer pageSize);
    /**
     * 处理一个申请
     * <p>
     * 会根据申请id，找到下一个需要处理的审批，并判断进行处理。<br/>
     * 如果用户不具有审批权限，则不会处理，并返回错误信息。
     * </p>
     *
     * @param id      申请id
     * @param userId  处理人id
     * @param status  处理结果
     * @param comment 处理意见
     * @return 结果消息
     */
    Result<String> handle(Integer id, Integer userId, Integer status, String comment);

    /**
     * 统计一个申请的未处理的审批数量
     * @param applicationId 申请id
     * @return 未处理的审批数量
     */
    Result<Long> getUnhandedCountByApplicationId(Integer applicationId);

    /**
     * 判断一个申请是否已经被审批通过。<br/>
     * 所有的审批都通过了才会返回true
     * @param applicationId 申请id
     * @return 是否已经审批通过
     */
    Result<Boolean> isApproved(Integer applicationId);

    /**
     * 判断一个申请是否已经被拒绝。<br/>
     * 只要有一个审批被拒绝了就会返回true
     * @param applicationId 申请id
     * @return 是否已经被拒绝
     */
    Result<Boolean> isRejected(Integer applicationId);
}
