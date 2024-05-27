package com.sheepion.mapper;

import com.sheepion.model.GiftType;
import com.sheepion.model.GiftType;
import java.util.List;

import com.sheepion.model.StreetExample;
import org.apache.ibatis.annotations.Param;

public interface StreetMapper {
//    long countByExample(GiftTypeExample example);

//    int deleteByExample(GiftTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GiftType giftType);

    int insertSelective(GiftType giftType);

    List<GiftType> selectByExample(String name);
    List<GiftType> selectByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    GiftType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GiftType record, @Param("example") StreetExample example);

    int updateByExample(@Param("record") GiftType record, @Param("example") StreetExample example);

    int updateByPrimaryKeySelective(GiftType record);

    int updateByPrimaryKey(GiftType record);
}
