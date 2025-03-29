package com.malskyi.kafka.controller.model;

import lombok.Data;

import java.util.List;

@Data
public class BookResponseDto {
    private Long bookId;
    private String title;
    private String genre;
    private String publishedYear;
    private AuthorData author;

    @Data
    public static class AuthorData {
        private Long authorId;
        private String firstName;
        private String lastName;
    }
}
