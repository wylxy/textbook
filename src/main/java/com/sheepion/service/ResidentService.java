package com.sheepion.service;

import com.sheepion.common.Result;
import com.sheepion.dto.CustomerDto;
import com.sheepion.dto.ResidentCreateDto;
import com.sheepion.model.Customer;

import java.util.List;

public interface ResidentService {
    /**
     * 通过id获取居民信息
     * @param id 居民id
     * @return 居民信息
     */
    Result<CustomerDto> getResidentInfoById(Integer id);

    /**
     * 添加居民
     * @param createDto 居民信息参数
     * @return 结果信息
     */
    Result<String> addResident(Customer customer);

    /**
     * 分页查询居民信息
     * @param page 页码
     * @param pageSize 每页大小
     * @return 居民信息列表
     */
    Result<List<CustomerDto>> list(String name, Integer page, Integer pageSize);

    /**
     * 通过id删除居民
     * @param id 居民id
     * @return 删除结果
     */
    Result<String> deleteResident(Integer id);

    /**
     * 通过id更新居民信息
     * @return 更新结果
     */
    Result<String> updateResidentInfoById(Customer customer);

    /**
     * 按名字模糊获取所有居民信息
     * @return 居民信息列表
     */
    Result<List<CustomerDto>> getAll(String name);
}
