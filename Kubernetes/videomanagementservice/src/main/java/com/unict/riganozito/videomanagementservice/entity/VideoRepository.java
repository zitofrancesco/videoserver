package com.unict.riganozito.videomanagementservice.entity;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<Video, Integer> {
    public List<Video> findByAuthor(User author);
}