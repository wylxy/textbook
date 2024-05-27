package com.sheepion.mapper;

import com.sheepion.model.Approver;
import com.sheepion.model.ApproverExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ApproverMapper {
    long countByExample(ApproverExample example);

    int deleteByExample(ApproverExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Approver record);

    int insertSelective(Approver record);

    List<Approver> selectByExample(ApproverExample example);

    Approver selectByPrimaryKey(Integer id);

    /**
     * 通过申请id查询下一级未处理的审批，例如一个申请有4级审批，1、2已经处理，3未处理，则返回第三级的审批信息
     *
     * @param applicationId 申请id
     * @return 下一级未处理的审批信息
     */
    Approver selectNextApprover(@Param("applicationId") Integer applicationId);

    /**
     * 获取用户的未处理的审批列表。
     * <p>
     * 同时满足以下要求才会被列出：<br/>
     * 1. 审批未被处理<br/>
     * 2. 前一级的审批已经通过<br/>
     * 3. 用户具有对应的审批权限<br/>
     * </p>
     *
     * @param userId 用户id
     * @return 审批列表
     */
    List<Approver> selectUnhandledByUserId(@Param("userId") Integer userId,
                                           @Param("type") Integer type,
                                           @Param("offset") Integer offset,
                                           @Param("pageSize") Integer pageSize);

    /**
     * <p>获取用户收到的所有的审批，包含已经处理的</p>
     * <p>
     * 同时满足以下要求才会被列出：<br/>
     * 1. 前一级的审批已经通过<br/>
     * 2. 用户具有对应的审批权限<br/>
     * </p>
     *
     * @param userId 用户id
     * @return 审批列表
     */
    List<Approver> selectReceivedApprovers(@Param("userId") Integer userId,
                                           @Param("type") Integer type,
                                           @Param("offset") Integer offset,
                                           @Param("pageSize") Integer pageSize);

    int updateByExampleSelective(@Param("record") Approver record, @Param("example") ApproverExample example);

    int updateByExample(@Param("record") Approver record, @Param("example") ApproverExample example);

    int updateByPrimaryKeySelective(Approver record);

    int updateByPrimaryKey(Approver record);

    int countReceivedApprovers(@Param("userId") Integer userId, @Param("type") Integer type);
}