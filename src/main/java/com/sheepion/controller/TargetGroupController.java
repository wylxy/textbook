package com.sheepion.controller;

import com.sheepion.common.HasPermission;
import com.sheepion.common.Result;
import com.sheepion.common.UserHolder;
import com.sheepion.dto.*;
import com.sheepion.model.Application;
import com.sheepion.service.TargetGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/target-group")
@Api(value = "TargetGroupController", tags = "重点人群管理模块")
public class TargetGroupController {
    @Autowired
    private TargetGroupService targetGroupService;

    @ApiOperation("查询分类列表")
    @GetMapping("/category")
    public Result<List<CategoryDto>> category() {
        return targetGroupService.getCategories();
    }

    @ApiOperation("统计重点人群数量")
    @GetMapping("/count")
    public Result<Integer> count() {
        return targetGroupService.count();
    }
    @ApiOperation("获取所有重点人群信息")
    @GetMapping("/all")
    public Result<List<TargetGroupDto>> getAll() {
        return targetGroupService.getAll();
    }

    @ApiOperation("获取重点人群信息")
    @GetMapping("/get")
    public Result<TargetGroupDto> get(@RequestParam Integer id) {
        return targetGroupService.getInfoById(id);
    }

    @ApiOperation("分页查询获取重点人群列表")
    @GetMapping("/list")
    public Result<List<TargetGroupDto>> list(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return targetGroupService.list(page, pageSize);
    }

    @ApiOperation("获取用户有权处理的申请的列表")
    @GetMapping("/application")
    public Result<List<ApplicationInfoDto>> listApplication(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return targetGroupService.listApplication(UserHolder.getCurrentUser(),page, pageSize);
    }
    @ApiOperation("获取有权处理的申请的个数")
    @GetMapping("/application/count")
    public Result<Integer> countApplication() {
        return targetGroupService.countApplication(UserHolder.getCurrentUser());
    }
    @ApiOperation("获取用户发起的申请的列表")
    @GetMapping("/submitted")
    public Result<List<ApplicationInfoDto>> listSubmitted(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return targetGroupService.listSubmitted(UserHolder.getCurrentUser(),page, pageSize);
    }

    @ApiOperation("发起添加重点人群申请")
    @PostMapping("/add")
    public Result<String> add(@RequestBody TargetGroupCreateDto targetGroupCreateDto) {
        return targetGroupService.add(targetGroupCreateDto);
    }

    @ApiOperation("修改重点人群信息")
    @PutMapping("/update")
    public Result<String> update(@RequestBody TargetGroupDto targetGroupDto) {
        return targetGroupService.updateById(targetGroupDto);
    }

    @ApiOperation("删除重点人群")
    @DeleteMapping("/delete")
    public Result<String> delete(@RequestParam Integer id) {
        return targetGroupService.deleteById(id);
    }

    @ApiOperation("审批重点人群申请")
    @PutMapping("/approve")
    //因为审批里面每一级审批都是不一样的权限，所以这里不加权限
    //@HasPermission("target-group.application.approve")
    public Result<String> approve(@RequestBody ApplicationApproveDto applicationApproveDto) {
        return targetGroupService.approve(applicationApproveDto);
    }

    @ApiOperation("查询审批状态")
    @GetMapping("/status")
    //不是自己的申请，不能查看
    //@HasPermission("target-group.application.status")
    public Result<ApplicationInfoDto> status(@RequestParam Integer id) {
        return targetGroupService.status(id);
    }

    //@ApiOperation("分配负责人")
    //@PutMapping("/assign")
    //public Result<String> assign(@RequestParam Integer id, @RequestParam String responsiblePerson) {
    //    System.out.println("id = " + id + ", responsiblePerson = " + responsiblePerson);
    //    return Result.failed("未完成，请使用修改信息来实现");
    //}
}
