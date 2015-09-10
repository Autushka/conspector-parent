package com.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by aautushk on 8/30/2015.
 */
public class UserRequestDto {

    @NotNull(message = "Field is mandatory")
    @NotEmpty(message = "Field can not be empty")
    @Pattern(regexp=".+@.+\\.[a-z]+", message = "Not a valid email value...")
    private String username;

    @NotNull(message = "Field is mandatory")
    @NotEmpty(message = "Field can not be empty")
    private String firstName;

    @NotNull(message = "Field is mandatory")
    @NotEmpty(message = "Field can not be empty")
    private String lastName;

    @NotNull(message = "Field is mandatory")
    @NotEmpty(message = "Field can not be empty")
    @Size(min = 6, message = "Password length should be at least 6 characters...")
    private String password;

//    public UserRequestDto(String username, String firstName, String lastName, String password){
//        this.username = username;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.password = password;
//    }

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
