package ru.rti.kettu.sbjava.mapping.soap;

import albumendpoint.Album;

final public class AlbumSoapMapping {

    private AlbumSoapMapping() {
    }

    public static Album mapSoapResponse(ru.rti.kettu.sbjava.model.db.h2.Album albumModel) {
        Album albumResponse = new Album();
        if (albumModel == null) return null;
        albumResponse.setId(albumModel.getId());
        albumResponse.setAuthor(albumModel.getAuthor());
        albumResponse.setName(albumModel.getName());
        albumResponse.setYear(albumModel.getYear());
        return albumResponse;
    }

    public static ru.rti.kettu.sbjava.model.db.h2.Album mapSoapRequest(Album albumRequest) {
        ru.rti.kettu.sbjava.model.db.h2.Album albumModel = new ru.rti.kettu.sbjava.model.db.h2.Album();
        if (albumRequest == null) return albumModel;
        albumModel.setId(albumRequest.getId());
        albumModel.setAuthor(albumRequest.getAuthor());
        albumModel.setName(albumRequest.getName());
        albumModel.setYear(albumRequest.getYear());
        return albumModel;
    }

    public static ru.rti.kettu.sbjava.model.db.h2.Album mapSoapRequest(Long id, String author, String name, int year) {
        ru.rti.kettu.sbjava.model.db.h2.Album albumModel = new ru.rti.kettu.sbjava.model.db.h2.Album();
        albumModel.setId(id);
        albumModel.setAuthor(author);
        albumModel.setName(name);
        albumModel.setYear(year);
        return albumModel;
    }
}
