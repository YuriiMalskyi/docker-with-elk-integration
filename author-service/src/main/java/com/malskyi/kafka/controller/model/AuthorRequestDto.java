package com.malskyi.kafka.controller.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AuthorRequestDto {
    private String firstName;
    private String lastName;
    private String biography;
    private LocalDate dateOfBirth;
    private String nationality;
    private List<Long> bookIds;
}
