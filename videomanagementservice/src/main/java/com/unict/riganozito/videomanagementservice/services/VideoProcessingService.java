package com.unict.riganozito.videomanagementservice.services;

import java.net.URI;

import com.unict.riganozito.videomanagementservice.entity.Video;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VideoProcessingService {

    @Value("${videoservice.videoprocessingservice}")
    public String host;

    public boolean videoProcess(Video video) {
        try {
            VideoProcessingServiceRequest obj = new VideoProcessingServiceRequest(video);

            RequestEntity<VideoProcessingServiceRequest> request = RequestEntity.post(new URI(host))
                    .contentType(MediaType.APPLICATION_JSON).body(obj);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);

            String messageResult = response.getBody();
            System.out.println(messageResult);

            if (response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is1xxInformational())
                return true;
            else
                return false;

        } catch (Exception ex) {
            return false;
        }
    }
}