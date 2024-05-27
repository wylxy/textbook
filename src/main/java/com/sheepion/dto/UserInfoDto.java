package com.sheepion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户信息")
public class UserInfoDto {
    @ApiModelProperty(value = "用户id")
    private Integer id;
    @ApiModelProperty( value = "用户名", notes = "3~10位字符")
    private String username;
    @ApiModelProperty( value = "用户姓名", notes = "不超过15个字符")
    private String name;
    private String password;
    private String newPass;
    @ApiModelProperty("用户联系方式")
    private String contactInfo;
    @ApiModelProperty("用户角色id")
    private Byte roleId;
    @ApiModelProperty("用户角色")
    private String role;
    @ApiModelProperty("用户所在街道id")
    private Integer streetId;
    @ApiModelProperty("用户所在街道名称")
    private String street;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", roleId=" + roleId +
                ", role='" + role + '\'' +
                ", streetId=" + streetId +
                ", street='" + street + '\'' +
                '}';
    }

    public Byte getRoleId() {
        return roleId;
    }

    public void setRoleId(Byte roleId) {
        this.roleId = roleId;
    }

    public Integer getStreetId() {
        return streetId;
    }

    public void setStreetId(Integer streetId) {
        this.streetId = streetId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
