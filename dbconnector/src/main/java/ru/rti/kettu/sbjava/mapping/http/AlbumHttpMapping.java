package ru.rti.kettu.sbjava.mapping.http;


import ru.rti.kettu.sbjava.model.db.h2.Album;

final public class AlbumHttpMapping {

    private AlbumHttpMapping() {
    }

    public static Album mapHttpRequest(Long id, String name, String author, int year) {
        Album albumModel = new Album();
        albumModel.setId(id);
        albumModel.setAuthor(author);
        albumModel.setName(name);
        albumModel.setYear(year);
        return albumModel;
    }
}
