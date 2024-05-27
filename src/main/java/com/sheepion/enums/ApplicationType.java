package com.sheepion.enums;

import lombok.Data;

/**
 * 申请类型，例如重点人群申请，低保申请等
 *
 * 系统添加新的需要流程审批的模块的时候，需要在这里加上一个枚举，用来和数据库对应上
 */
public enum ApplicationType {
    //重点人群
    TARGET_GROUP(0, "重点人群"),
    ;
    private final Integer code;
    private final String type;

    ApplicationType(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public static ApplicationType getByCode(Integer code) {
        for (ApplicationType applicationType : ApplicationType.values()) {
            if (applicationType.code.equals(code)) {
                return applicationType;
            }
        }
        return null;
    }

    public static ApplicationType getByType(String type) {
        for (ApplicationType applicationType : ApplicationType.values()) {
            if (applicationType.type.equals(type)) {
                return applicationType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ApplicationType{" +
                "code=" + code +
                ", type='" + type + '\'' +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
