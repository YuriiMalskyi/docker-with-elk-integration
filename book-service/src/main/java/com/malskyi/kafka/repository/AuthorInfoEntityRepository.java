package com.malskyi.kafka.repository;

import com.malskyi.kafka.enitites.AuthorInfoEntity;
import org.springframework.data.repository.CrudRepository;

//@Repository
public interface AuthorInfoEntityRepository extends CrudRepository<AuthorInfoEntity, Long> {
}
