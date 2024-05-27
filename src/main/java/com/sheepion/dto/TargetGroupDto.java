package com.sheepion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "重点人群")
public class TargetGroupDto {
    @ApiModelProperty(value = "重点人员id")
    private Integer id;
    @ApiModelProperty(value = "重点人员姓名")
    private String name;
    @ApiModelProperty(value = "分类id")
    private Integer categoryId;
    @ApiModelProperty(value = "分类")
    private String category;
    @ApiModelProperty(value = "联系方式")
    private String contactInfo;
    @ApiModelProperty(value = "所在街道id")
    private Integer streetId;
    @ApiModelProperty(value = "所在街道")
    private String street;
    @ApiModelProperty(value = "负责人Id")
    private Integer responsiblePersonId;
    @ApiModelProperty(value = "负责人")
    private String responsiblePerson;
}
