package com.speakbuddy.service.soundreceiverapi.repository.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "MAPPING_USERS_PHRASES")
public class MappingUsersPhrase {
    @EmbeddedId
    private MappingUsersPhraseId id;

    @ColumnDefault("LOCALTIMESTAMP")
    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "TIMESTAMP default now()")
    @Generated()
    private Instant createdAt;

    @Column(name = "UPDATED_AT")
    private Instant updatedAt;

    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @Column(name = "UPDATED_BY")
    private Integer updatedBy;

    @Lob
    @Column(name = "ORIGINAL_FILE_PATH", nullable = false)
    private String originalFilePath;

    @Lob
    @Column(name = "CONVERTED_FILE_PATH")
    private String convertedFilePath;

}