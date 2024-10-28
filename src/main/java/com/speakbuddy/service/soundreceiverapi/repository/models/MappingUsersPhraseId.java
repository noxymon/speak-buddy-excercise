package com.speakbuddy.service.soundreceiverapi.repository.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class MappingUsersPhraseId implements java.io.Serializable {

    @Column(name = "USER_ID", nullable = false)
    private Integer userId;

    @Column(name = "PHRASE_ID", nullable = false)
    private Integer phraseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MappingUsersPhraseId entity = (MappingUsersPhraseId) o;
        return Objects.equals(this.phraseId, entity.phraseId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phraseId, userId);
    }

}