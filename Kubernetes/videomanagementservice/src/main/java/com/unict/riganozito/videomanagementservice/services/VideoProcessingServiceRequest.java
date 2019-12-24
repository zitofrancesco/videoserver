package com.unict.riganozito.videomanagementservice.services;

import java.io.Serializable;

import com.unict.riganozito.videomanagementservice.entity.Video;

public class VideoProcessingServiceRequest implements Serializable {
    private Integer videoId;

    public VideoProcessingServiceRequest() {
    }

    public VideoProcessingServiceRequest(Video video) {
        videoId = video.getId();
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }
}