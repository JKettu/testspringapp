package ru.rti.kettu.sbjava.mapping.soap;

import musicendpoint.Album;

final public class AlbumMapping {

    private AlbumMapping() {
    }

    public static Album mapResponse(ru.rti.kettu.sbjava.model.Album albumModel) {
        Album albumResponse = new Album();
        if (albumModel == null) return albumResponse;
        albumResponse.setId(albumModel.getId());
        albumResponse.setAuthor(albumModel.getAuthor());
        albumResponse.setName(albumModel.getName());
        albumResponse.setYear(albumModel.getYear());
        return albumResponse;
    }

    public static ru.rti.kettu.sbjava.model.Album mapRequest(Album albumRequest) {
        ru.rti.kettu.sbjava.model.Album albumModel = new ru.rti.kettu.sbjava.model.Album();
        if (albumRequest == null) return albumModel;
        albumModel.setId(albumRequest.getId());
        albumModel.setAuthor(albumRequest.getAuthor());
        albumModel.setName(albumRequest.getName());
        albumModel.setYear(albumRequest.getYear());
        return albumModel;
    }


}
