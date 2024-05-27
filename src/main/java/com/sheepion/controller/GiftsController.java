package com.sheepion.controller;

import com.sheepion.common.Result;
import com.sheepion.dto.CustomerDto;
import com.sheepion.model.Customer;
import com.sheepion.model.Gifts;
import com.sheepion.model.Log;
import com.sheepion.service.GiftsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "ResidentController", tags = "赠品管理模块")
@RestController()
@RequestMapping("/api/gifts")
public class GiftsController {
    @Autowired
    private GiftsService giftsService;

    @ApiOperation("按名字模糊获取所有赠品信息")
    @GetMapping("/all")
    public Result<List<Gifts>> getAll(@RequestParam(required = false) String name){
        return giftsService.getAll(name);
    }

    @ApiOperation("通过id获取赠品信息")
    @GetMapping("/get")
    public Result<Gifts> get(@RequestParam Integer id) {
        return giftsService.getResidentInfoById(id);
    }

    @ApiOperation("分页模糊查询赠品信息")
    @GetMapping("/list")
    public Result<List<Gifts>> list(@RequestParam(required = false)String name, @RequestParam Integer page, @RequestParam Integer size) {
        return giftsService.list(name,page, size);
    }
    @ApiOperation("查看日志")
    @GetMapping("/logs")
    public Result<List<Log>> logs(@RequestParam Integer page, @RequestParam Integer size) {
        return giftsService.logs(page, size);
    }
    @ApiOperation("添加赠品")
    @PostMapping("/add")
    public Result<String> add(@RequestBody Gifts gifts) {
        return giftsService.addResident(gifts);
    }

    @ApiOperation("删除赠品")
    @DeleteMapping("/delete")
    public Result<String> delete(@RequestParam Integer id) {
        return giftsService.deleteResident(id);
    }

    @ApiOperation("更新赠品信息")
    @PutMapping("/update")
    public Result<String> update(@RequestBody Gifts gifts) {
        return giftsService.updateResidentInfoById(gifts);
    }
}
