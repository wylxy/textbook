package com.sheepion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "街道信息")
public class StreetDto {
    @ApiModelProperty(value = "街道id")
    private Integer id;
    @ApiModelProperty(value = "街道名")
    private String name;
    @ApiModelProperty(value = "描述")
    private String description;
}
