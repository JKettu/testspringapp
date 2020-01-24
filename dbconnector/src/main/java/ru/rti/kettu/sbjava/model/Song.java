package ru.rti.kettu.sbjava.model;


import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Document
public class Song {

    @Id
    @GeneratedValue
    private String id;
    @Column(length = 50)
    private String name;
    private Long albumId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
}
