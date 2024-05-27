package com.sheepion.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("居民创建参数")
public class ResidentCreateDto {
    @ApiModelProperty("居民id")
    private Integer id;
    @ApiModelProperty(value = "居民姓名", required = true)
    private String name;
    @ApiModelProperty(value = "身份证号", required = true)
    private String idNumber;
    @ApiModelProperty("联系方式")
    private String contactInfo;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("街道id")
    private Integer streetId;
    @ApiModelProperty("年收入")
    private Integer income;
}
