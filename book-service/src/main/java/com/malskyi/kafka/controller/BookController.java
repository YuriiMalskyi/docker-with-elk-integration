package com.malskyi.kafka.controller;

import com.malskyi.kafka.controller.model.BookRequestDto;
import com.malskyi.kafka.controller.model.BookResponseDto;
import com.malskyi.kafka.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable("id") Long id) {
        BookResponseDto bookResponseDto = bookService.getBook(id);
        return ResponseEntity.ok(bookResponseDto);
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> saveBook(@RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto bookResponseDto = bookService.saveBook(bookRequestDto);
        return ResponseEntity.ok(bookResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable("id") Long bookId, @RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto bookResponseDto = bookService.updateBook(bookId, bookRequestDto);
        return ResponseEntity.ok(bookResponseDto);
    }
}
