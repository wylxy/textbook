package com.sheepion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("重点人群审批参数")
public class ApplicationApproveDto {
    @ApiModelProperty(value = "申请id", required = true)
    private Integer applicationId;
    @ApiModelProperty(value = "处理结果id/状态id", required = true)
    private Integer statusId;
    @ApiModelProperty(value = "处理意见", required = true)
    private String comment;
}
