package com.sheepion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "注册参数")
public class RegisterDto {

    @ApiModelProperty(value = "用户名", required = true,notes = "3~10位字符")
    String username;
    @ApiModelProperty(value = "密码", required = true)
    String password;
    @ApiModelProperty(value = "姓名", required = true,notes = "不超过15个字符")
    String name;
    @ApiModelProperty(value = "联系方式", required = true)
    String contactInfo;
    @ApiModelProperty(value = "角色id", required = true)
    Byte role;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RegisterDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", role=" + role +
                '}';
    }
}
