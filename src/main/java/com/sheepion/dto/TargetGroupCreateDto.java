package com.sheepion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "重点人群创建参数")
public class TargetGroupCreateDto {
    @ApiModelProperty(value = "居民id", required = true)
    private Integer residentId;
    @ApiModelProperty(value = "分类id", required = true)
    private Integer categoryId;
    @ApiModelProperty(value = "负责人id", required = true)
    private Integer responsiblePersonId;
    @ApiModelProperty(value = "所在街道id", required = true)
    private Integer streetId;
    @ApiModelProperty(value = "申请理由", required = true)
    private String reason;
}
