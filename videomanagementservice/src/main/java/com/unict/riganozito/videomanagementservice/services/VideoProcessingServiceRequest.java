package com.unict.riganozito.videomanagementservice.services;

import java.io.Serializable;

import com.unict.riganozito.videomanagementservice.entity.Video;

public class VideoProcessingServiceRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8833788493221015270L;
    private Integer videoId;

    public VideoProcessingServiceRequest() {
    }

    public VideoProcessingServiceRequest(Video video) {
        videoId = video.getId();
    }

    public Integer getVideold() {
        return videoId;
    }

    public void setVideold(Integer videoId) {
        this.videoId = videoId;
    }
}