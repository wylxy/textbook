package com.sheepion.service;

import com.sheepion.common.Result;
import com.sheepion.dto.CustomerDto;
import com.sheepion.model.Customer;
import com.sheepion.model.Gifts;
import com.sheepion.model.Log;

import java.util.List;

public interface GiftsService {
    /**
     * 通过id获取赠品信息
     * @param id 赠品id
     * @return 赠品信息
     */
    Result<Gifts> getResidentInfoById(Integer id);

    /**
     * 添加赠品
     * @return 结果信息
     */
    Result<String> addResident(Gifts gifts);

    /**
     * 分页查询赠品信息
     * @param page 页码
     * @param pageSize 每页大小
     * @return 赠品信息列表
     */
    Result<List<Gifts>> list(String name, Integer page, Integer pageSize);
    Result<List<Log>> logs(Integer page, Integer pageSize);

    /**
     * 通过id删除赠品
     * @param id 赠品id
     * @return 删除结果
     */
    Result<String> deleteResident(Integer id);

    /**
     * 通过id更新赠品信息
     * @return 更新结果
     */
    Result<String> updateResidentInfoById(Gifts gifts);

    /**
     * 按名字模糊获取所有赠品信息
     * @return 赠品信息列表
     */
    Result<List<Gifts>> getAll(String name);
}
