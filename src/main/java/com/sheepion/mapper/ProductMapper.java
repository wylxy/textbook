package com.sheepion.mapper;

import com.sheepion.model.Gifts;
import com.sheepion.model.Order;
import com.sheepion.model.Product;
import com.sheepion.model.ResidentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Order order);

    List<Gifts> selectByExample(ResidentExample example);

    Order selectByPrimaryKey(Integer id);


    int updateById(@Param("product") Product product);

    List<Product> list(@Param("name")String name, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
    List<Product> listEvaluate(@Param("name")String name, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
    List<Product> listEvaluateYear(@Param("name")String name, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);


}
