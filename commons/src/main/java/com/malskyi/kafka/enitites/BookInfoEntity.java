package com.malskyi.kafka.enitites;

//import jakarta.persistence.*;
import javax.persistence.*;
import lombok.Data;

import java.text.MessageFormat;

@Entity(name = "book_info")
@Data
public class BookInfoEntity {

    @Id
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity authorEntity;

    @Override
    public String toString() {
        return MessageFormat.format("'{'bookId={0}, title=''{1}'', genre=''{2}'}'", bookId, title, genre);
    }
}