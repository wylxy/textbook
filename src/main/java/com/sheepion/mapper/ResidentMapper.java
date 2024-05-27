package com.sheepion.mapper;

import com.sheepion.model.Customer;
import com.sheepion.model.ResidentExample;
import java.util.List;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

public interface ResidentMapper {
    Integer countByExample(@Param("name") String name);

    int deleteByExample(ResidentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    List<Customer> selectByExample(ResidentExample example);

    Customer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Customer record, @Param("example") ResidentExample example);

    int updateByExample(@Param("record") Customer record, @Param("example") ResidentExample example);

    int updateByPrimaryKeySelective(Customer customer);

    int updateByPrimaryKey(Customer record);

    List<Customer> list(@Param("name")String name, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
}
