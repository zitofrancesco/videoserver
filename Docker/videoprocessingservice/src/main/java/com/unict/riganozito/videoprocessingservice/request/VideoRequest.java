package com.unict.riganozito.videoprocessingservice.request;

import java.io.Serializable;

public class VideoRequest implements Serializable {
    private Integer videoId;

    public VideoRequest() {
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }
}