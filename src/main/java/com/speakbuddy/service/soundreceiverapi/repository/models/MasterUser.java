package com.speakbuddy.service.soundreceiverapi.repository.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "MASTER_USERS")
public class MasterUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;

    @Lob
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Lob
    @Column(name = "PASSWORD")
    private String password;

    @ColumnDefault("TRUE")
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = false;

    @ColumnDefault("LOCALTIMESTAMP")
    @Column(name = "REGISTERED_AT", nullable = false)
    private Instant registeredAt;

    @Column(name = "UPDATED_AT")
    private Instant updatedAt;

}