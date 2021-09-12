package com.semantica.yada.fileuploadservice.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StorageService {

    String store(UUID fileId, MultipartFile file);

}
