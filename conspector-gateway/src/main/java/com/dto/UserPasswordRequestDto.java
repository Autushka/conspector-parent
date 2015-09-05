package com.dto;

/**
 * Created by aautushk on 9/5/2015.
 */
public class UserPasswordRequestDto {
    private String currentPassword;
    private String newPassword;

    public String getCurrentPassword(){
        return this.currentPassword;
    }
    public void setCurrentPassword(String value){
        this.currentPassword = value;
    }

    public String getNewPassword(){
        return this.newPassword;
    }
    public void setNewPassword(String value){
        this.newPassword = value;
    }
}
