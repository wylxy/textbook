package com.sheepion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("多级审批详细信息")
public class ApproverInfoDto {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("申请id")
    private Integer applicationId;
    @ApiModelProperty("审批级数")
    private Integer level;
    @ApiModelProperty("审批状态id")
    private Integer statusId;
    @ApiModelProperty("审批状态")
    private String status;
    @ApiModelProperty("审批人id")
    private Integer userId;
    @ApiModelProperty("审批人姓名")
    private String userName;
    @ApiModelProperty("审批意见")
    private String comment;
    @ApiModelProperty("审批时间")
    private Date date;
}
