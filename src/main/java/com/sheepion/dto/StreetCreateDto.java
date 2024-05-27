package com.sheepion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("街道创建参数")
public class StreetCreateDto {
    @ApiModelProperty(value = "街道名称", required = true)
    private String name;
    @ApiModelProperty(value = "街道描述", required = true)
    private String description;
}
