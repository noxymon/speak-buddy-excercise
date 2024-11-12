package com.speakbuddy.service.soundreceiverapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * BaseRepository interface that extends JpaRepository.
 * This interface is annotated with @NoRepositoryBean to indicate that Spring Data JPA should not create an instance of it.
 *
 * Benefits:
 * - Provides a common base interface for all repositories, promoting code reuse and consistency.
 * - Simplifies the creation of new repositories by extending this base interface.
 *
 * @param <T>  the type of the entity
 * @param <ID> the type of the entity's identifier, which must be serializable
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
}