package com.unict.riganozito.videomanagementservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {

    @Value("${videoservice.storage:${user.home}}")
    public String directory;

    public boolean storeFile(Integer id, MultipartFile file) {

        try {
            Path copyLocation = Paths.get(directory + File.separator + id.toString() + File.separator + "video.mp4");
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}