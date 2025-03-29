package com.malskyi.kafka.service;

import com.malskyi.kafka.controller.model.AuthorRequestDto;
import com.malskyi.kafka.controller.model.AuthorResponseDto;
import com.malskyi.kafka.enitites.AuthorEntity;
import com.malskyi.kafka.enitites.BookInfoEntity;
import com.malskyi.kafka.exception.AuthorNotFoundException;
import com.malskyi.kafka.repository.AuthorEntityRepository;
import com.malskyi.kafka.repository.BooksInfoEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorEntityRepository authorEntityRepository;
    private final BooksInfoEntityRepository booksInfoEntityRepository;

    public AuthorResponseDto getAuthor(Long authorId) {
        final AuthorEntity author = authorEntityRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author with id: [%s] doesn't exist", authorId)));
        return map(author);
    }

    public AuthorResponseDto saveAuthor(AuthorRequestDto authorRequestDto) {
        final AuthorEntity newAuthor = buildAuthor(authorRequestDto);
        final AuthorEntity createdAuthor = authorEntityRepository.save(newAuthor);
        // todo send kafka event
        return map(createdAuthor);
    }

    public AuthorResponseDto updateAuthor(Long authorId, AuthorRequestDto authorRequestDto) {
        AuthorEntity author = authorEntityRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found"));

        author.setFirstName(authorRequestDto.getFirstName());
        author.setLastName(authorRequestDto.getLastName());
        author.setBiography(authorRequestDto.getBiography());
        author.setDateOfBirth(authorRequestDto.getDateOfBirth());
        author.setNationality(authorRequestDto.getNationality());
        author.setBooksInfoEntities(getBookInfoEntities(authorRequestDto.getBookIds()));

        AuthorEntity updatedAuthor = authorEntityRepository.save(author);
        // todo send kafka event
        return map(updatedAuthor);
    }

    private AuthorEntity buildAuthor(AuthorRequestDto authorRequestDto) {
        final AuthorEntity author = new AuthorEntity();
        author.setFirstName(authorRequestDto.getFirstName());
        author.setLastName(authorRequestDto.getLastName());
        author.setBiography(authorRequestDto.getBiography());
        author.setDateOfBirth(authorRequestDto.getDateOfBirth());
        author.setNationality(authorRequestDto.getNationality());
        author.setBooksInfoEntities(getBookInfoEntities(authorRequestDto.getBookIds()));
        return author;
    }

    private List<BookInfoEntity> getBookInfoEntities(List<Long> bookIds) {
        List<BookInfoEntity> bookInfoEntities = StreamSupport.stream(booksInfoEntityRepository.findAllById(bookIds).spliterator(), false)
                .toList();
        if (bookInfoEntities.size() < bookIds.size()) {
            String missingBooksStr = bookInfoEntities.stream()
                    .filter(bookInfoEntity -> bookIds.contains(bookInfoEntity.getBookId()))
                    .map(BookInfoEntity::toString)
                    .collect(Collectors.joining(" | ", "[", "]"));
            throw new IllegalArgumentException(String.format("Could not process, some books are missing in the DB: %s", missingBooksStr));
        }
        return bookInfoEntities;
    }

    private AuthorResponseDto map(AuthorEntity author) {
        AuthorResponseDto response = new AuthorResponseDto();
        response.setAuthorId(author.getAuthorId());
        response.setFirstName(author.getFirstName());
        response.setLastName(author.getLastName());
        response.setBiography(author.getBiography());
        response.setDateOfBirth(author.getDateOfBirth());
        response.setNationality(author.getNationality());
        response.setBookInfoDtoList(map(author.getBooksInfoEntities()));
        return response;
    }

    private List<AuthorResponseDto.BookInfoDto> map(List<BookInfoEntity> booksInfoEntities) {
        return booksInfoEntities.stream()
                .map(this::map)
                .toList();
    }

    private AuthorResponseDto.BookInfoDto map(BookInfoEntity bookInfoEntity) {
        final AuthorResponseDto.BookInfoDto bookInfoDto = new AuthorResponseDto.BookInfoDto();
        bookInfoDto.setBookId(bookInfoEntity.getBookId());
        bookInfoDto.setTitle(bookInfoEntity.getTitle());
        bookInfoDto.setGenre(bookInfoEntity.getGenre());
        return bookInfoDto;
    }
}

