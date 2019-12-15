package com.unict.riganozito.videomanagementservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "The name parameter must not be blank!")
    @Column(unique = true)
    private String username;

    @NotNull(message = "The password parameter must not be blank!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
