package com.malskyi.kafka.repository;

import com.malskyi.kafka.enitites.BookInfoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksInfoEntityRepository extends CrudRepository<BookInfoEntity, Long> {
    @Query(value = "SELECT * FROM BookInfoEntity b WHERE b.author_id = ?1", nativeQuery = true)
    List<BookInfoEntity> findByAuthorId(Long authorId);
}
