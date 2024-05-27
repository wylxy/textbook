package com.sheepion.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "申请状态")
public enum ApplicationStatus {
    // 已发出申请但未处理
    @ApiModelProperty(value = "待处理")
    PENDING(0, "待处理"),
    // 通过
    @ApiModelProperty(value = "通过")
    PASS(1, "通过"),
    // 拒绝
    @ApiModelProperty(value = "拒绝")
    REJECT(2, "拒绝"),
    ;

    private final Integer code;
    private final String status;

    ApplicationStatus(Integer code, String status) {
        this.code = code;
        this.status = status;
    }

    public static ApplicationStatus getStatusByCode(Integer code) {
        for (ApplicationStatus applicationStatus : ApplicationStatus.values()) {
            if (applicationStatus.code.equals(code)) {
                return applicationStatus;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ApplicationStatus{" +
                "code=" + code +
                ", status='" + status + '\'' +
                '}';
    }
}
