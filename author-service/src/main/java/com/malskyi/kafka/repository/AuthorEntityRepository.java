package com.malskyi.kafka.repository;

import com.malskyi.kafka.enitites.AuthorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorEntityRepository extends CrudRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> findById(Long id);
}
