package com.malskyi.kafka.controller;

import com.malskyi.kafka.controller.model.AuthorRequestDto;
import com.malskyi.kafka.controller.model.AuthorResponseDto;
import com.malskyi.kafka.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthor(@PathVariable("id") Long id) {
        AuthorResponseDto authorResponseDto = authorService.getAuthor(id);
        return ResponseEntity.ok(authorResponseDto);
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> saveAuthor(@RequestBody AuthorRequestDto authorRequestDto) {
        AuthorResponseDto authorResponseDto = authorService.saveAuthor(authorRequestDto);
        return ResponseEntity.ok(authorResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(@PathVariable("id") Long authorId, @RequestBody AuthorRequestDto authorRequestDto) {
        AuthorResponseDto authorResponseDto = authorService.updateAuthor(authorId, authorRequestDto);
        return ResponseEntity.ok(authorResponseDto);
    }
}
