package com.malskyi.kafka.controller.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AuthorResponseDto {
    private Long authorId;
    private String firstName;
    private String lastName;
    private String biography;
    private LocalDate dateOfBirth;
    private String nationality;
    private List<BookInfoDto> bookInfoDtoList;

    @Data
    public static class BookInfoDto {
        private Long bookId;
        private String title;
        private String genre;
    }
}
