package com.semantica.yada.fileuploadservice;

import com.semantica.yada.fileuploadservice.storage.StorageFileNotFoundException;
import com.semantica.yada.fileuploadservice.storage.StorageService;
import com.semantica.yada.fileuploadservice.util.FileChecksum;
import com.semantica.yada.fileuploadservice.util.FileMimeType;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/fileuploadservice/v1")
public class FileUploadServiceController {

    private final StorageService storageService;
    private final CommandGateway commandGateway;

    @PostMapping("/")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {

        UUID fileId = UUID.randomUUID();
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String newFilename = storageService.store(fileId, file);
        File newFile = new File(newFilename);

        commandGateway.send(new UploadFileCommand(
                  fileId
                , originalFilename
                , FileMimeType.getMimeType(newFile)
                , file.getSize()
                , new Date()
                , FileChecksum.getChecksum(newFilename)
                , file.getName()
        ));

        return ResponseEntity.accepted().build();
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<?> handleWrongParameter(MultipartException e) {
        return ResponseEntity.badRequest().build();
    }
}
