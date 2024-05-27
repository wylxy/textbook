package com.sheepion.common;

import java.util.ArrayList;
import java.util.List;

public class UserHolder {
    private static ThreadLocal<Integer> currentUser=new ThreadLocal<>();
    private static ThreadLocal<Byte> currentRoleId=new ThreadLocal<>();
    private static ThreadLocal<List<String>> currentPermissions=new ThreadLocal<>();
    public static void setCurrentPermissions(List<String> permissions){
        currentPermissions.set(permissions);
    }
    public static List<String> getCurrentPermissions(){
        if(currentPermissions.get()==null){
            currentPermissions.set(new ArrayList<>());
        }
        return currentPermissions.get();
    }
    public static void setCurrentUser(Integer userId){
        currentUser.set(userId);
    }
    public static Integer getCurrentUser(){
        return currentUser.get();
    }
    public static void setCurrentRoleId(Byte roleId){
        currentRoleId.set(roleId);
    }
    public static Byte getCurrentRoleId(){
        return currentRoleId.get();
    }
}
