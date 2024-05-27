package com.sheepion.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("权限分配参数")
public class PermissionAssignDto {
    private Integer roleId;
    private Integer[] permissionIds;
}
