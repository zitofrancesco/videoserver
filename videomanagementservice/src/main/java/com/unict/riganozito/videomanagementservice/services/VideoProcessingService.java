package com.unict.riganozito.videomanagementservice.services;

import com.unict.riganozito.videomanagementservice.entity.Video;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VideoProcessingService {

    @Value("${videoservice.videoprocessingservice}")
    public String host;

    public boolean videoProcess(Video video) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<VideoProcessingServiceRequest> request = new HttpEntity<>(
                    new VideoProcessingServiceRequest(video), headers);

            ResponseEntity<VideoProcessingServiceRequest> responce = restTemplate.postForEntity(host, request,
                    VideoProcessingServiceRequest.class);
            if (responce.getStatusCode().is2xxSuccessful())
                return true;
            else
                return false;

        } catch (Exception ex) {
            return false;
        }
    }
}