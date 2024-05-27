package com.sheepion.mapper;

import com.sheepion.model.Application;
import com.sheepion.model.ApplicationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApplicationMapper {
    long countByExample(ApplicationExample example);

    int deleteByExample(ApplicationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Application record);

    int insertSelective(Application record);

    List<Application> selectByExampleWithBLOBs(ApplicationExample example);

    List<Application> selectByExample(ApplicationExample example);

    Application selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Application record, @Param("example") ApplicationExample example);

    int updateByExampleWithBLOBs(@Param("record") Application record, @Param("example") ApplicationExample example);

    int updateByExample(@Param("record") Application record, @Param("example") ApplicationExample example);

    int updateByPrimaryKeySelective(Application record);

    int updateByPrimaryKeyWithBLOBs(Application record);

    int updateByPrimaryKey(Application record);

    /**
     * 查询用户提交的申请
     * @param userId 用户id
     * @param type 申请类型
     * @param offset 分页偏移量
     * @param pageSize 分页大小
     * @return
     */
    List<Application> selectSubmittedByUserId(Integer userId, Integer type, Integer offset, Integer pageSize);
}