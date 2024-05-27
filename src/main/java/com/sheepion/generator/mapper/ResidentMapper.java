package com.sheepion.generator.mapper;

import com.sheepion.generator.model.Resident;
import com.sheepion.generator.model.ResidentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ResidentMapper {
    long countByExample(ResidentExample example);

    int deleteByExample(ResidentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Resident record);

    int insertSelective(Resident record);

    List<Resident> selectByExample(ResidentExample example);

    Resident selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Resident record, @Param("example") ResidentExample example);

    int updateByExample(@Param("record") Resident record, @Param("example") ResidentExample example);

    int updateByPrimaryKeySelective(Resident record);

    int updateByPrimaryKey(Resident record);
}