package com.sheepion.controller;

import com.sheepion.common.Result;
import com.sheepion.dto.CustomerDto;
import com.sheepion.dto.ResidentCreateDto;
import com.sheepion.model.Customer;
import com.sheepion.service.ResidentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "ResidentController", tags = "客户管理模块")
@RestController()
@RequestMapping("/api/resident")
public class ResidentController {
    @Autowired
    private ResidentService residentService;

    @ApiOperation("按名字模糊获取所有客户信息")
    @GetMapping("/all")
    public Result<List<CustomerDto>> getAll(@RequestParam(required = false) String name){
        return residentService.getAll(name);
    }

    @ApiOperation("通过id获取客户信息")
    @GetMapping("/get")
    public Result<CustomerDto> get(@RequestParam Integer id) {
        return residentService.getResidentInfoById(id);
    }

    @ApiOperation("分页模糊查询客户信息")
    @GetMapping("/list")
    public Result<List<CustomerDto>> list(@RequestParam(required = false)String name, @RequestParam Integer page, @RequestParam Integer size) {
        return residentService.list(name,page,size);
    }

    @ApiOperation("添加客户")
    @PostMapping("/add")
    public Result<String> add(@RequestBody Customer customer) {
        return residentService.addResident(customer);
    }

    @ApiOperation("删除客户")
    @DeleteMapping("/delete")
    public Result<String> delete(@RequestParam Integer id) {
        return residentService.deleteResident(id);
    }

    @ApiOperation("更新客户信息")
    @PutMapping("/update")
    public Result<String> update(@RequestBody Customer customer) {
        return residentService.updateResidentInfoById(customer);
    }
}
