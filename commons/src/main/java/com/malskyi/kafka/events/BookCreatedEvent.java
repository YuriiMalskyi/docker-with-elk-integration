package com.malskyi.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreatedEvent {
    private Long bookId;
    private String title;
    private String genre;
    private Long authorId;
}
