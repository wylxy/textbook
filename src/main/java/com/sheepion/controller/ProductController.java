package com.sheepion.controller;

import com.sheepion.common.Result;
import com.sheepion.model.Gifts;
import com.sheepion.model.Order;
import com.sheepion.model.Product;
import com.sheepion.service.GiftsService;
import com.sheepion.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "ResidentController", tags = "订单管理模块")
@RestController()
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

//    @ApiOperation("按名字模糊获取所有赠品信息")
//    @GetMapping("/all")
//    public Result<List<Gifts>> getAll(@RequestParam(required = false) String name){
//        return productService.getAll(name);
//    }

    @ApiOperation("通过id获取订单信息")
    @GetMapping("/get")
    public Result<Order> get(@RequestParam Integer id) {
        return productService.getInfoById(id);
    }

    @ApiOperation("分页模糊查询赠品信息")
    @GetMapping("/list")
    public Result<List<Product>> list(@RequestParam(required = false)String name, @RequestParam Integer page, @RequestParam Integer size) {
        return productService.list(name,page, size);
    }
    @ApiOperation("分页模糊查询效益评价(月)")
    @GetMapping("/listEvaluate")
    public Result<List<Product>> listEvaluate(@RequestParam(required = false)String name, @RequestParam Integer page, @RequestParam Integer size) {
        return productService.listEvaluate(name,page, size);
    }
    @ApiOperation("分页模糊查询效益评价(年)")
    @GetMapping("/listEvaluateYear")
    public Result<List<Product>> listEvaluateYear(@RequestParam(required = false)String name, @RequestParam Integer page, @RequestParam Integer size) {
        return productService.listEvaluateYear(name,page, size);
    }
    @ApiOperation("添加赠品")
    @PostMapping("/add")
    public Result<String> add(@RequestBody Order order ) {
        Result<String> add = productService.add(order);
        return add;
    }
    @ApiOperation("确认收货")
    @PostMapping("/edit")
    public Result<String> edit(@RequestBody Product product ) {
        Result<String> add = productService.updateProductInfoById(product);
        return add;
    }

//    @ApiOperation("删除赠品")
//    @DeleteMapping("/delete")
//    public Result<String> delete(@RequestParam Integer id) {
//        return productService.deleteResident(id);
//    }

//    @ApiOperation("更新赠品信息")
//    @PutMapping("/update")
//    public Result<String> update(@RequestBody Gifts gifts) {
//        return productService.updateResidentInfoById(gifts);
//    }
}
