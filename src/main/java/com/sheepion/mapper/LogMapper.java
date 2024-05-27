package com.sheepion.mapper;

import com.sheepion.model.Gifts;
import com.sheepion.model.Log;
import com.sheepion.model.ResidentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogMapper {



    int insert(Log log);
}
