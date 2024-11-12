package com.speakbuddy.service.soundreceiverapi.config;

import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.jobrunr.utils.mapper.jackson.JacksonJsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for JobRunr.
 * This class sets up the storage provider for JobRunr jobs.
 * If any other `StorageProvider` is needed to scale JobRunr, it should be configured here.
 */
@Configuration
public class JobrunrConfig {

    /**
     * Creates and configures an in-memory storage provider for JobRunr.
     *
     * @param jobMapper the JobMapper to use for mapping jobs
     * @return the configured StorageProvider
     */
    @Bean
    public StorageProvider storageProvider(JobMapper jobMapper) {
        InMemoryStorageProvider storageProvider = new InMemoryStorageProvider();
        storageProvider.setJobMapper(new JobMapper(new JacksonJsonMapper()));
        return storageProvider;
    }
}