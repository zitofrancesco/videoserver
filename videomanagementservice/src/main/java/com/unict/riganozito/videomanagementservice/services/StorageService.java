package com.unict.riganozito.videomanagementservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.unict.riganozito.videomanagementservice.entity.Video;

@Service
@Transactional
public class StorageService {

    @Value("${videoservice.storage:${user.home}}")
    public String directory;

    public String getExtensionOfFile(String fileName) {
        String fileExtension = "";
        if (fileName.contains(".") && fileName.lastIndexOf(".") != 0) {
            fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return fileExtension;
    }

    public boolean checkVideo(MultipartFile file) {
        String ext = getExtensionOfFile(file.getOriginalFilename());
        if (!ext.equals("mp4"))
            return false;
        return true;
    }

    public boolean storeVideo(Video video, MultipartFile file) {

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
        Path base = Paths.get(directory, "var", "video", video.getId().toString());
        File baseFile = base.toFile();
        if (!baseFile.exists())
            baseFile.mkdir();
        Path location = Paths.get(directory, "var", "video", video.getId().toString(), "video.mp4");
        return location;
    }

    public Path getRelativePath(Video video) {
        Path pathAbsolute = getAbsolutePath(video);
        Path pathBase = Paths.get(directory);
        Path pathRelative = pathBase.relativize(pathAbsolute);
        return pathRelative;
    }
}