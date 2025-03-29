package com.malskyi.kafka.enitites;

import lombok.Data;
//import jakarta.persistence.*;
import javax.persistence.*;

@Data
@Entity(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "publication_date")
    private String publicationDate; // Changed to String

    @Column(name = "genre")
    private String genre;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorInfoEntity authorInfoEntity;
}