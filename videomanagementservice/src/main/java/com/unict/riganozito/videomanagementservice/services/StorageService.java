package com.unict.riganozito.videomanagementservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.unict.riganozito.videomanagementservice.entity.Video;

@Service
public class StorageService {

    @Value("${videoservice.storage:${user.home}}")
    public String directory;

    public boolean storeFile(Video video, MultipartFile file) {

        try {
            Path copyLocation = getAbsolutePath(video);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Path getAbsolutePath(Video video) {
        Path location = Paths.get(directory, video.getId().toString(), "video.mp4");
        return location;
    }

    public Path getRelativePath(Video video) {
        Path folder = getAbsolutePath(video);
        Path nameFolder = folder.getParent();
        Path location = folder.relativize(nameFolder);
        return location;
    }
}