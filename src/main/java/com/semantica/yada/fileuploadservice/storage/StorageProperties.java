package com.semantica.yada.fileuploadservice.storage;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class StorageProperties {
    private @Getter String location = "/tmp/upload-dir";
    private @Getter Integer depth = 2;
    private @Getter Integer length = 2;
}
