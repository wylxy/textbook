package com.sheepion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("角色创建参数")
public class RoleCreateDto {
    @ApiModelProperty("角色名")
    private String name;
    @ApiModelProperty("角色描述")
    private String description;
    @ApiModelProperty("需要分配的角色权限")
    private List<Integer> permissionIds;
}
