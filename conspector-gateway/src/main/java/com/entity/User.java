package com.entity;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by aautushk on 8/30/2015.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    public String getUsername() {
        return username;
    }
    public void setUsername(String value){
        this.username = value;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String value){
        this.firstName = value;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String value){
        this.lastName = value;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String value){
        this.password =  value;
    }

    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled){
        this.enabled = enabled;
    }
}
