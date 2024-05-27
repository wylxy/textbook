package com.sheepion.generator.mapper;

import com.sheepion.generator.model.GovermentLog;
import com.sheepion.generator.model.GovermentLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GovermentLogMapper {
    long countByExample(GovermentLogExample example);

    int deleteByExample(GovermentLogExample example);

    int deleteByPrimaryKey(Integer logId);

    int insert(GovermentLog record);

    int insertSelective(GovermentLog record);

    List<GovermentLog> selectByExample(GovermentLogExample example);

    GovermentLog selectByPrimaryKey(Integer logId);

    int updateByExampleSelective(@Param("record") GovermentLog record, @Param("example") GovermentLogExample example);

    int updateByExample(@Param("record") GovermentLog record, @Param("example") GovermentLogExample example);

    int updateByPrimaryKeySelective(GovermentLog record);

    int updateByPrimaryKey(GovermentLog record);
}