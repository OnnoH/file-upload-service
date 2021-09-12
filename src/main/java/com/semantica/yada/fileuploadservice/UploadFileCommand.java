package com.semantica.yada.fileuploadservice;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;
import java.util.UUID;

@Data
public class UploadFileCommand {

    @TargetAggregateIdentifier
    private final UUID fileId;

    private final String name;
    private final String mimeType;
    private final Long size;
    private final Date creationDate;
    private final String hash;
    private final String originalPath;

}
