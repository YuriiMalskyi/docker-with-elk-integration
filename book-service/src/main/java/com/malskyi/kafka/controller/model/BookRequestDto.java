package com.malskyi.kafka.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {
    private String title;
    private String isbn;
    private String genre;
    private String publicationDate;
    private String description;
    private Long authorId;
}
