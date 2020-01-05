package com.unict.riganozito.videomanagementservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Video {

    public static final String STATE_WAITING_UPLOAD = "WaitingUpload";
    public static final String STATE_UPLOADED = "Uploaded";
    public static final String STATE_NOT_AVAILABLE = "NotAvailable";
    public static final String STATE_AVAILABLE = "Available";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String author;

    @NotNull()
    private String status;

    @NotNull
    @ManyToOne
    @JsonIgnore
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Transient
    @JsonProperty("username")
    public String getUsername() {
        return user.getUsername();
    }

}
