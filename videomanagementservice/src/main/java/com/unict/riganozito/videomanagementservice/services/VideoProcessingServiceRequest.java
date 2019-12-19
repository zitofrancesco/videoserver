package com.unict.riganozito.videomanagementservice.services;

import java.io.Serializable;

import com.unict.riganozito.videomanagementservice.entity.Video;

public class VideoProcessingServiceRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8833788493221015270L;
    private Integer videold;

    public VideoProcessingServiceRequest() {
    }

    public VideoProcessingServiceRequest(Video video) {
        videold = video.getId();
    }

    public Integer getVideold() {
        return videold;
    }

    public void setVideold(Integer videold) {
        this.videold = videold;
    }
}