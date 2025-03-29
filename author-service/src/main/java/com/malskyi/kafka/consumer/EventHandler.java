package com.malskyi.kafka.consumer;

import com.malskyi.kafka.enitites.AuthorEntity;
import com.malskyi.kafka.enitites.BookInfoEntity;
import com.malskyi.kafka.events.BookCreatedEvent;
import com.malskyi.kafka.exception.AuthorNotFoundException;
import com.malskyi.kafka.repository.AuthorEntityRepository;
import com.malskyi.kafka.repository.BooksInfoEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventHandler {
    private final AuthorEntityRepository authorEntityRepository;
    private final BooksInfoEntityRepository booksInfoEntityRepository;

    // BookUpdatedHandler
    // BookCreatedHandler

    public void handleEvents(List<Object> events) {
        log.info("Processing events: [{}]", events);
        for (Object event : events) {
            if (event instanceof BookCreatedEvent) {
                handleBookCreatedEvent((BookCreatedEvent) event);
            } else {
                log.error("Could not process event due to no supported resolver found. Event: {}", o);
            }
        }
    }

    private void handleBookCreatedEvent(BookCreatedEvent bookCreatedEvent) {
        BookInfoEntity bookInfoEntity = map(bookCreatedEvent);
        booksInfoEntityRepository.save(bookInfoEntity);
    }

    private BookInfoEntity map(BookCreatedEvent bookCreatedEvent) {
        BookInfoEntity entity = new BookInfoEntity();
        entity.setBookId(bookCreatedEvent.getBookId());
        entity.setTitle(bookCreatedEvent.getTitle());
        entity.setGenre(bookCreatedEvent.getGenre());

        AuthorEntity authorEntity = authorEntityRepository.findById(bookCreatedEvent.getAuthorId()).orElseThrow(() ->
                new AuthorNotFoundException(String.format("Author with id: [%s] doesn't exist", bookCreatedEvent.getAuthorId())));
        entity.setAuthorEntity(authorEntity);

        return entity;
    }
}
