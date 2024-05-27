package com.sheepion.mapper;

import com.sheepion.model.ApprovalLevel;
import com.sheepion.model.ApprovalLevelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApprovalLevelMapper {
    long countByExample(ApprovalLevelExample example);

    int deleteByExample(ApprovalLevelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApprovalLevel record);

    int insertSelective(ApprovalLevel record);

    List<ApprovalLevel> selectByExample(ApprovalLevelExample example);

    ApprovalLevel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApprovalLevel record, @Param("example") ApprovalLevelExample example);

    int updateByExample(@Param("record") ApprovalLevel record, @Param("example") ApprovalLevelExample example);

    int updateByPrimaryKeySelective(ApprovalLevel record);

    int updateByPrimaryKey(ApprovalLevel record);
}