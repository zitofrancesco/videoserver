package com.unict.riganozito.videomanagementservice.entity;

import java.io.Serializable;

public class VideoProcessingServiceEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8833788493221015270L;
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