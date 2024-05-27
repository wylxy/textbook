package com.sheepion.generator.mapper;

import com.sheepion.generator.model.Approver;
import com.sheepion.generator.model.ApproverExample;
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

    int updateByExampleSelective(@Param("record") Approver record, @Param("example") ApproverExample example);

    int updateByExample(@Param("record") Approver record, @Param("example") ApproverExample example);

    int updateByPrimaryKeySelective(Approver record);

    int updateByPrimaryKey(Approver record);
}