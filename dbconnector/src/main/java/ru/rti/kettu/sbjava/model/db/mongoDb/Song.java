package ru.rti.kettu.sbjava.model.db.mongoDb;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Song {

    @Id
    private String id;
    @Column(length = 50)
    private String name;
    private Long albumId;
}
