package ru.rti.kettu.sbjava.mapping.moduleintegration;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class KafkaOperationAlbumApi {
    private Long id;
    private String name;
    private String author;
    private Integer year;

    @JsonCreator
    public KafkaOperationAlbumApi(Long id, String name, String author, Integer year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }
}
