package com.semantica.yada.fileuploadservice;

import io.dekorate.kubernetes.annotation.KubernetesApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.semantica.yada.fileuploadservice.storage.StorageProperties;

@SpringBootApplication
@KubernetesApplication(name = "file-upload-service")
@EnableConfigurationProperties(StorageProperties.class)
public class FileUploadService {

    public static void main(String[] args) {
        SpringApplication.run(FileUploadService.class, args);
    }

}
