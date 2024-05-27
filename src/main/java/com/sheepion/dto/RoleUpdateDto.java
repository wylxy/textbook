package com.sheepion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "角色更新参数")
public class RoleUpdateDto {
    @ApiModelProperty(value = "用户id", required = true)
    private Integer id;
    @ApiModelProperty(value = "角色id", required = true)
    private Integer roleId;
}
