package com.dto;

/**
 * Created by aautushk on 8/30/2015.
 */
public class UserResponseDto {
    private String username;
    private Boolean enabled;

    public String getUserName(){
        return this.username;
    }
    public void setUserName(String username){
        this.username = username;
    }

    public Boolean getEnabled(){
        return this.enabled;
    }
    public void setEnabled(Boolean enabled){
        this.enabled = enabled;
    }
}
