package com.sheepion.mapper;

import com.sheepion.model.Gifts;
import com.sheepion.model.Log;
import com.sheepion.model.ResidentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GiftsMapper {


    int deleteByPrimaryKey(Integer id);

    int insertSelective(Gifts gifts);

    List<Gifts> selectByExample(ResidentExample example);

    Gifts selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(Gifts gifts);

    List<Gifts> list(@Param("name")String name, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
    List<Log> logs(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

}
