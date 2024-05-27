package com.sheepion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "客户信息")
public class CustomerDto {
    @ApiModelProperty(value = "id")
    private Integer customerId;
    @ApiModelProperty(value = "姓名")
    private String customerName;
    @ApiModelProperty(value = "地址")
    private String customerAddress;
    @ApiModelProperty(value = "联系方式")
    private String customerPhone;
    @ApiModelProperty(value = "密码")
    private String customerPassword;
    @ApiModelProperty(value = "总数")
    private Integer total;
}
