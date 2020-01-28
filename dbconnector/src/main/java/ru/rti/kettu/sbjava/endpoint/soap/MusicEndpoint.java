package ru.rti.kettu.sbjava.endpoint.soap;

import musicendpoint.*;
import org.hibernate.sql.Update;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.rti.kettu.sbjava.model.db.h2.Album;
import ru.rti.kettu.sbjava.service.MusicService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;
import static ru.rti.kettu.sbjava.mapping.soap.AlbumMapping.mapResponse;

@Endpoint
public class MusicEndpoint {
    private static final String NAMESPACE_URI = "musicEndpoint";

    final MusicService musicService;

    public MusicEndpoint(MusicService musicService) {
        this.musicService = musicService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAlbumRequest")
    @ResponsePayload
    public GetAlbumResponse getAlbum(@RequestPayload GetAlbumRequest request) {
        GetAlbumResponse response = new GetAlbumResponse();
        if (request.getId() != null)
            response.getAlbum().add(mapResponse(musicService.getAlbumById(request.getId())));
        else {
            List<Album> albumsListModel = musicService.getAllAlbums();
            if (isEmpty(albumsListModel)) return new GetAlbumResponse();
            List<musicendpoint.Album> albums = new ArrayList<>();
            albumsListModel.forEach( album -> {
                albums.add(mapResponse(album));
            });
            response.getAlbum().addAll(albums);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAlbumRequest")
    @ResponsePayload
    public CreateAlbumResponse createAlbum(@RequestPayload CreateAlbumRequest request) {
        CreateAlbumResponse response = new CreateAlbumResponse();

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAlbumRequest")
    @ResponsePayload
    public UpdateAlbumResponse updateAlbum(@RequestPayload UpdateAlbumRequest request) {
        UpdateAlbumResponse response = new UpdateAlbumResponse();

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAlbumRequest")
    @ResponsePayload
    public DeleteAlbumResponse deleteAlbum(@RequestPayload DeleteAlbumRequest request) {
        DeleteAlbumResponse response = new DeleteAlbumResponse();

        return response;
    }
}
