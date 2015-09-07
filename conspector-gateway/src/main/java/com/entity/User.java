package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by aautushk on 8/30/2015.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "firstName", length = 100)
    private String firstName;

    @Column(name = "lastName", length = 100)
    private String lastName;

    @Column(name = "password", length = 100)
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
