package com.semantica.yada.fileuploadservice;

import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.WeakReferenceCache;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FileUploadCommandConfiguration {

    @Bean
    public Repository<FileUploadAggregate> fileUploadRepository(EventStore eventStore, Cache cache) {
        return EventSourcingRepository.builder(FileUploadAggregate.class)
                .cache(cache)
                .eventStore(eventStore)
                .build();
    }

    @Bean
    public Cache cache() {
        return new WeakReferenceCache();
    }
}