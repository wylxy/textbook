package com.sheepion.controller;

import com.sheepion.common.Result;
import com.sheepion.dto.StreetCreateDto;
import com.sheepion.dto.StreetDto;
import com.sheepion.model.GiftType;
import com.sheepion.service.StreetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/street")
@Api(value = "StreetController", tags = "赠品类别管理模块")
public class StreetController {
    @Autowired
    private StreetService streetService;

//    @ApiOperation("按名字模糊获取所有赠品类别信息")
//    @GetMapping("/all")
//    public Result<List<StreetDto>> getAll(@RequestParam(required = false) String name) {
//        return streetService.getAll(name);
//    }

//    @ApiOperation("统计赠品类别数量")
//    @GetMapping("/count")
//    public Result<Integer> count() {
//        return streetService.count();
//    }

    @ApiOperation("分页查询赠品类别列表")
    @GetMapping("/list")
    public Result<List<GiftType>> list(@RequestParam("page") Integer page,
                                       @RequestParam("pageSize") Integer pageSize) {
        return streetService.list(page, pageSize);
    }

    @ApiOperation("通过id查询赠品类别信息")
    @GetMapping("/get")
    public Result<GiftType> getById(@RequestParam("id") Integer id) {
        return streetService.getById(id);
    }

    @ApiOperation("添加赠品类别")
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody GiftType dto) {
        return streetService.add(dto);
    }

    @ApiOperation("修改赠品类别信息")
    @PutMapping("/update")
    public Result<String> update(@RequestBody GiftType dto) {
        return streetService.updateById(dto);
    }

    @ApiOperation("删除赠品类别")
    @DeleteMapping("/delete")
    public Result<String> delete(@RequestParam("id") Integer id) {
        return streetService.deleteById(id);
    }
}
