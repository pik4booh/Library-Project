package io.bootify.library.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

        public String store(MultipartFile file) throws IOException{
            if (file.isEmpty()) {
                    throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
            }

            //generate random name for file
            String fileName = java.util.UUID.randomUUID().toString();
            Path path = Paths.get("src/main/resources/static/images/upload/" + fileName);
            Files.copy(file.getInputStream(), path);
            return fileName;
        }
}

    
