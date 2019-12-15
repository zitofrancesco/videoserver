package com.unict.riganozito.videomanagementservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String author;

    @NotNull()
    @Value("empty")
    private String status;

    @NotNull
    @ManyToOne
    private User user;

    public Integer getId() {
        return id;
    }

    public Video setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Video setName(String name) {
        this.name = name;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Video setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Video setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Video setUser(User user) {
        this.user = user;
        return this;
    }

    public User getUser() {
        return user;
    }

}
