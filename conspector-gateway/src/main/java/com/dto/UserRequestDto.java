package com.dto;

/**
 * Created by aautushk on 8/30/2015.
 */
public class UserRequestDto {
    private String username;
    private String firstName;
    private String lastName;
    private String password;

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String value){
        this.username = value;
    }

    public String getFirstName(){
        return this.firstName;
    }
    public void setFirstName(String value){
        this.firstName = value;
    }

    public String getLastName(){
        return this.lastName;
    }
    public void setLastName(String value){
        this.lastName = value;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String value){
        this.password = value;
    }
}
