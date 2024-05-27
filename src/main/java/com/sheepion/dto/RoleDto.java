package com.sheepion.dto;

import com.sheepion.model.Permission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("角色")
public class RoleDto {
    @ApiModelProperty("角色id")
    private Integer id;
    @ApiModelProperty("角色名")
    private String name;
    @ApiModelProperty("角色描述")
    private String description;
    @ApiModelProperty("角色权限列表")
    private List<Permission> permissions;
}
