package com.sheepion.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "角色id对应关系")
public enum Role {
    // 管理员(政府）
    @ApiModelProperty(value = "管理员(政府）")
    ADMINISTRATOR((byte) 1, "管理员"),
    //社区管理员
    @ApiModelProperty(value = "社区管理员")
    COMMUNITY_ADMINISTRATOR((byte) 2, "社区管理员"),
    //社区工作人员
    @ApiModelProperty(value = "社区工作人员")
    COMMUNITY_STAFF((byte) 3, "社区工作人员"),
    //街道工作人员
    STREET_STAFF((byte) 4, "街道工作人员"),
    ;

    private final byte code;
    private final String name;

    Role(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Role getRoleByCode(Integer code) {
        for (Role role : Role.values()) {
            if (role.code == code) {
                return role;
            }
        }
        return null;
    }
    public static String getRoleNameByCode(Integer code) {
        for (Role role : Role.values()) {
            if (role.code == code) {
                return role.name;
            }
        }
        return null;
    }
    public byte getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
