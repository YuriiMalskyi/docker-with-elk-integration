package com.malskyi.kafka.enitites;

import lombok.Data;
//import jakarta.persistence.*;
import javax.persistence.*;

import java.util.List;

@Entity(name = "author_info")
@Data
public class AuthorInfoEntity {

    @Id
    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "authorInfoEntity")
    private List<BookEntity> bookEntities;
}