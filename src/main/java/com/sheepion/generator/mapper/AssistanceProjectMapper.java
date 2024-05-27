package com.sheepion.generator.mapper;

import com.sheepion.generator.model.AssistanceProject;
import com.sheepion.generator.model.AssistanceProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AssistanceProjectMapper {
    long countByExample(AssistanceProjectExample example);

    int deleteByExample(AssistanceProjectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AssistanceProject record);

    int insertSelective(AssistanceProject record);

    List<AssistanceProject> selectByExample(AssistanceProjectExample example);

    AssistanceProject selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AssistanceProject record, @Param("example") AssistanceProjectExample example);

    int updateByExample(@Param("record") AssistanceProject record, @Param("example") AssistanceProjectExample example);

    int updateByPrimaryKeySelective(AssistanceProject record);

    int updateByPrimaryKey(AssistanceProject record);
}