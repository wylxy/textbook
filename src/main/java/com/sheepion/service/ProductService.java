package com.sheepion.service;

import com.sheepion.common.Result;
import com.sheepion.model.Gifts;
import com.sheepion.model.Order;
import com.sheepion.model.Product;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface ProductService {
    /**
     * 通过id获取赠品信息
     * @param id 赠品id
     * @return 赠品信息
     */
    Result<Order> getInfoById(Integer id);

    /**
     * 添加赠品
     * @return 结果信息
     */
    Result<String> add(Order order);

    /**
     * 分页查询赠品信息
     * @param page 页码
     * @param pageSize 每页大小
     * @return 赠品信息列表
     */
    Result<List<Product>> list(String name, Integer page, Integer pageSize);
    Result<List<Product>> listEvaluate(String name, Integer page, Integer pageSize);
    Result<List<Product>> listEvaluateYear(String name, Integer page, Integer pageSize);

//
//    /**
//     * 通过id删除赠品
//     * @param id 赠品id
//     * @return 删除结果
//     */
//    Result<String> deleteResident(Integer id);
//
    /**
     * 更新商品
     * @return 更新结果
     */
    Result<String> updateProductInfoById(Product product);
//
//    /**
//     * 按名字模糊获取所有赠品信息
//     * @return 赠品信息列表
//     */
//    Result<List<Gifts>> getAll(String name);
}
