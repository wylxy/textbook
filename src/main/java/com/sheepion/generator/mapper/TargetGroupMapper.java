package com.sheepion.generator.mapper;

import com.sheepion.generator.model.TargetGroup;
import com.sheepion.generator.model.TargetGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TargetGroupMapper {
    long countByExample(TargetGroupExample example);

    int deleteByExample(TargetGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TargetGroup record);

    int insertSelective(TargetGroup record);

    List<TargetGroup> selectByExample(TargetGroupExample example);

    TargetGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TargetGroup record, @Param("example") TargetGroupExample example);

    int updateByExample(@Param("record") TargetGroup record, @Param("example") TargetGroupExample example);

    int updateByPrimaryKeySelective(TargetGroup record);

    int updateByPrimaryKey(TargetGroup record);
}