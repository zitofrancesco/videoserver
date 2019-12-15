package com.unict.riganozito.videomanagementservice.services;

import java.io.Serializable;

import com.unict.riganozito.videomanagementservice.entity.Video;

class VideoProcessingServiceEntity implements Serializable {
    private Integer videold;

    public VideoProcessingServiceEntity() {
    }

    public VideoProcessingServiceEntity(Video video) {
        videold = video.getId();
    }

    public Integer getVideold() {
        return videold;
    }

    public void setVideold(Integer videold) {
        this.videold = videold;
    }
}