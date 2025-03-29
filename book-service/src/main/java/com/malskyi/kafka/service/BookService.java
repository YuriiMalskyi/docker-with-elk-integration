package com.malskyi.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malskyi.kafka.controller.model.BookRequestDto;
import com.malskyi.kafka.enitites.AuthorInfoEntity;
import com.malskyi.kafka.enitites.BookEntity;
import com.malskyi.kafka.events.BookCreatedEvent;
import com.malskyi.kafka.exception.AuthorNotFoundException;
import com.malskyi.kafka.exception.BookNotFoundException;
import com.malskyi.kafka.repository.AuthorInfoEntityRepository;
import com.malskyi.kafka.repository.BookEntityRepository;
import com.malskyi.kafka.controller.model.BookResponseDto;
import com.malskyi.kafka.service.publisher.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class BookService {
    private final String bookEventTopicName;
    private final BookEntityRepository bookEntityRepository;
    private final AuthorInfoEntityRepository authorInfoEntityRepository;
    private final KafkaEventPublisher bookEventPublisher;

    public BookService(@Value("${com.malskyi.kafka.topic.bookBus.name}") String bookEventTopicName, BookEntityRepository bookEntityRepository,
                       AuthorInfoEntityRepository authorInfoEntityRepository, KafkaEventPublisher bookEventPublisher) {
        this.bookEventTopicName = bookEventTopicName;
        this.bookEntityRepository = bookEntityRepository;
        this.authorInfoEntityRepository = authorInfoEntityRepository;
        this.bookEventPublisher = bookEventPublisher;
    }

    public BookResponseDto getBook(Long bookId) {
        // у випадку якщо книжку не знайдено - генеруємо власну кастомну помилку
        final BookEntity book = bookEntityRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(String.format("Book with id: [%s] doesn't exist", bookId)));
        return map(book);
    }

    public BookResponseDto saveBook(BookRequestDto bookRequestDto) {
        final BookEntity newBook = buildBook(bookRequestDto);
        final BookEntity createdBook = bookEntityRepository.save(newBook);

        bookEventPublisher.publish(bookEventTopicName, createdBook.getBookId().toString(),
                buildBookCreatedEvent(createdBook), Map.of("eventType", BookCreatedEvent.class.getName()));
        return map(createdBook);
    }

    private BookCreatedEvent buildBookCreatedEvent(BookEntity createdBook) {
        return new BookCreatedEvent(createdBook.getBookId(), createdBook.getTitle(), createdBook.getGenre(),
                createdBook.getAuthorInfoEntity().getAuthorId());
    }

    public BookResponseDto updateBook(Long bookId, BookRequestDto bookRequestDto) {
        BookEntity book = bookEntityRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        book.setTitle(bookRequestDto.getTitle());
        book.setGenre(bookRequestDto.getGenre());
        book.setPublicationDate(bookRequestDto.getPublicationDate());
        book.setAuthorInfoEntity(getAuthorInfoEntity(bookRequestDto.getAuthorId()));

        BookEntity updatedBook = bookEntityRepository.save(book);
        // todo send kafka event
        return map(updatedBook);
    }

    private BookResponseDto map(BookEntity book) {
        BookResponseDto response = new BookResponseDto();
        response.setBookId(book.getBookId());
        response.setTitle(book.getTitle());
        response.setGenre(book.getGenre());
        response.setPublishedYear(book.getPublicationDate());
        response.setAuthor(map(book.getAuthorInfoEntity()));
        return response;
    }

    private BookResponseDto.AuthorData map(AuthorInfoEntity author) {
        BookResponseDto.AuthorData authorData = new BookResponseDto.AuthorData();
        authorData.setAuthorId(author.getAuthorId());
        authorData.setFirstName(author.getFirstName());
        authorData.setLastName(author.getLastName());
        return authorData;
    }

    private BookEntity buildBook(BookRequestDto bookRequestDto) {
        final BookEntity book = new BookEntity();
        book.setTitle(bookRequestDto.getTitle());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setGenre(bookRequestDto.getGenre());
        book.setPublicationDate(bookRequestDto.getPublicationDate());
        book.setDescription(bookRequestDto.getDescription());
        book.setAuthorInfoEntity(getAuthorInfoEntity(bookRequestDto.getAuthorId()));
        return book;
    }

    private AuthorInfoEntity getAuthorInfoEntity(Long authorId) {
        // у випадку якщо автора не знайдено - генеруємо власну кастомну помилку
        return authorInfoEntityRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with id: [%s] doesn't exists", authorId)));
    }
}
