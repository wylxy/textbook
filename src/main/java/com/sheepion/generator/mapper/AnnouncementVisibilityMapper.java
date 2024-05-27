package com.sheepion.generator.mapper;

import com.sheepion.generator.model.AnnouncementVisibility;
import com.sheepion.generator.model.AnnouncementVisibilityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AnnouncementVisibilityMapper {
    long countByExample(AnnouncementVisibilityExample example);

    int deleteByExample(AnnouncementVisibilityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AnnouncementVisibility record);

    int insertSelective(AnnouncementVisibility record);

    List<AnnouncementVisibility> selectByExample(AnnouncementVisibilityExample example);

    AnnouncementVisibility selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AnnouncementVisibility record, @Param("example") AnnouncementVisibilityExample example);

    int updateByExample(@Param("record") AnnouncementVisibility record, @Param("example") AnnouncementVisibilityExample example);

    int updateByPrimaryKeySelective(AnnouncementVisibility record);

    int updateByPrimaryKey(AnnouncementVisibility record);
}