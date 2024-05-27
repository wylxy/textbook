package com.sheepion.service;

import com.sheepion.common.Result;
import com.sheepion.dto.StreetCreateDto;
import com.sheepion.dto.StreetDto;
import com.sheepion.model.GiftType;

import java.util.List;

public interface StreetService {
    /**
     * 获取街道列表
     * @return 街道列表
     */
    Result<List<GiftType>> list(Integer page, Integer pageSize);

    /**
     * 通过id查询街道信息
     * @param id 街道id
     * @return 街道信息
     */

    Result<GiftType> getById(Integer id);
//
//    /**
//     * 统计街道数量
//     * @return 街道数量
//     */
//    Result<Integer> count();
//
//    /**
//     * 添加街道
//     * @param streetCreateParam 街道创建参数
//     * @return 街道id，如果添加失败返回null
//     */
    Result<Integer> add(GiftType giftType);
//
//    /**
//     * 通过id修改街道信息
//     * @param dto 新的街道信息
//     * @return 修改结果
//     */
//
    Result<String> updateById(GiftType giftType);
//
//    /**
//     * 通过id删除街道
//     * @param id 街道id
//     * @return 删除结果
//     */
    Result<String> deleteById(Integer id);
//
//    /**
//     * 获取所有街道信息
//     * @return 街道信息列表
//     */
    Result<List<GiftType>> getAll(String name);
}
