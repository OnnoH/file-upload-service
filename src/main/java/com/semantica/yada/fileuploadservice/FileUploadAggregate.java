package com.semantica.yada.fileuploadservice;

import com.semantica.yada.events.FileUploadedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;
import java.util.UUID;

@Aggregate
public class FileUploadAggregate {

    @AggregateIdentifier
    private UUID fileId;

    private String name;
    private String mimeType;
    private Long size;
    private Date creationDate;
    private String hash;
    private String originalPath;

    public FileUploadAggregate() {
    }

    @CommandHandler
    public FileUploadAggregate(UploadFileCommand cmd) {
        AggregateLifecycle.apply(new FileUploadedEvent(
                  cmd.getFileId()
                , cmd.getName()
                , cmd.getMimeType()
                , cmd.getSize()
                , cmd.getCreationDate()
                , cmd.getHash()
                , cmd.getOriginalPath()
        ));
    }

    @EventSourcingHandler
    public void on(FileUploadedEvent evt) {
        fileId = evt.getFileId();
    }
}
