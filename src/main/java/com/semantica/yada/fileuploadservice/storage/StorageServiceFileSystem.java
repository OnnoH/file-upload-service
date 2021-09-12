package com.semantica.yada.fileuploadservice.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageServiceFileSystem implements StorageService {

    private final Path rootLocation;
    private final Integer depth;
    private Integer length;

    @Autowired
    public StorageServiceFileSystem(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.depth = properties.getDepth();
        this.length = properties.getLength();
    }

    @Override
    public String store(UUID fileId, MultipartFile file) {

//        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String filename = fileId.toString();
        String filepath = getStorageLocation(filename);

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException("Cannot store file with relative path outside current directory " + filename);
            }
            if (createStorageLocation(filepath)) {
                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, this.rootLocation.resolve(filepath + filename), StandardCopyOption.REPLACE_EXISTING);
                }
            }

        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

        return filepath + filename;

    }

    private boolean createStorageLocation(String filepath) {

        File newTarget = new File(filepath);

        if (!newTarget.exists()) {
            if (!newTarget.mkdirs()) {
                throw new StorageException("Cannot create file path " + filepath);
            }
        }

        return newTarget.exists();
    }

    private String getStorageLocation(String filename) {
        if (filename.length() < this.length * this.depth) {
            this.length = Math.round(filename.length() / this.depth);
        }

        String filepath = this.rootLocation.toString() + File.separator;
        for (int i = 0; i < this.depth; i++) {
            filepath += filename.substring(i * this.length, (i * this.length) + this.length) + File.separator   ;
        }

        return filepath;
    }

}
