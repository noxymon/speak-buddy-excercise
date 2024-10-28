package com.speakbuddy.service.soundreceiverapi.repository.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "MASTER_PHRASES")
public class MasterPhrase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "NAME", nullable = false)
    private String name;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @ColumnDefault("LOCALTIMESTAMP")
    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

    @Column(name = "UPDATED_AT")
    private Instant updatedAt;

    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @Column(name = "UPDATED_BY")
    private Integer updatedBy;

    @ColumnDefault("TRUE")
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = false;

}