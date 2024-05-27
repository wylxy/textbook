package com.sheepion.mapper;

import com.sheepion.model.Administrator;
import com.sheepion.model.AdministratorExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AdministratorMapper {
    long countByExample(AdministratorExample example);

    int deleteByExample(AdministratorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Administrator record);

    int insertSelective(Administrator record);

    List<Administrator> selectByExample(AdministratorExample example);

    List<Administrator> selectByPage(@Param("name") String name, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
    List<Administrator> selectByName(@Param("name") String name,@Param("password") String password);

    Administrator selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Administrator record, @Param("example") AdministratorExample example);

    int updateByExample(@Param("record") Administrator record, @Param("example") AdministratorExample example);

    int updateByPrimaryKeySelective(Administrator record);

    int updateByPrimaryKey(Administrator record);
    int updateByName(@Param("record") Administrator record);

}
