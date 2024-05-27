package com.sheepion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("申请详细信息")
public class ApplicationInfoDto {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("申请人id")
    private Integer userId;
    @ApiModelProperty("申请人姓名")
    private String userName;
    @ApiModelProperty("申请理由")
    private String reason;
    @ApiModelProperty("申请时间")
    private Date date;
    @ApiModelProperty("申请状态id")
    private Integer statusId;
    @ApiModelProperty("申请状态")
    private String status;
    @ApiModelProperty("多级审批信息")
    private List<ApproverInfoDto> approvers;
    @ApiModelProperty("申请内容")
    private String payload;

}
