package com.malskyi.kafka.repository;

import com.malskyi.kafka.enitites.BookEntity;
import org.springframework.data.repository.CrudRepository;

//@Repository
public interface BookEntityRepository extends CrudRepository<BookEntity, Long> {
}
