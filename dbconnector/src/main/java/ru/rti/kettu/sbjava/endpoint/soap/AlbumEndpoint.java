package ru.rti.kettu.sbjava.endpoint.soap;

import albumendpoint.*;
import org.springframework.util.ObjectUtils;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.rti.kettu.sbjava.model.db.h2.Album;
import ru.rti.kettu.sbjava.service.MusicService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;
import static ru.rti.kettu.sbjava.mapping.soap.AlbumSoapMapping.mapSoapRequest;
import static ru.rti.kettu.sbjava.mapping.soap.AlbumSoapMapping.mapSoapResponse;

@Endpoint
public class AlbumEndpoint {
    private static final String NAMESPACE_URI = "albumEndpoint";

    final MusicService musicService;

    public AlbumEndpoint(MusicService musicService) {
        this.musicService = musicService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAlbumRequest")
    @ResponsePayload
    public GetAlbumResponse getAlbum(@RequestPayload GetAlbumRequest request) {
        GetAlbumResponse response = new GetAlbumResponse();
        if (request.getId() != null)
            response.getAlbum().add(mapSoapResponse(musicService.getAlbumById(request.getId())));
        else {
            List<Album> albumsListModel = musicService.getAllAlbums();
            if (isEmpty(albumsListModel)) return null;
            List<albumendpoint.Album> albums = new ArrayList<>();
            albumsListModel.forEach(album -> {
                albums.add(mapSoapResponse(album));
            });
            response.getAlbum().addAll(albums);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAlbumRequest")
    @ResponsePayload
    public CreateAlbumResponse createAlbum(@RequestPayload CreateAlbumRequest request) {
        CreateAlbumResponse response = new CreateAlbumResponse();
        Album albumModel = mapSoapRequest(null, request.getAuthor(), request.getName(), request.getYear());
        String albumId = musicService.createAlbumInfo(albumModel);
        try {
            albumendpoint.Album album = mapSoapResponse(musicService.getAlbumById(Long.parseLong(albumId)));
            response.setAlbum(album);
            return response;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAlbumRequest")
    @ResponsePayload
    public UpdateAlbumResponse updateAlbum(@RequestPayload UpdateAlbumRequest request) {
        UpdateAlbumResponse response = new UpdateAlbumResponse();
        Album albumModel = mapSoapRequest(request.getAlbum());
        response.setAlbum(mapSoapResponse(musicService.updateAlbum(albumModel)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAlbumRequest")
    @ResponsePayload
    public DeleteAlbumResponse deleteAlbum(@RequestPayload DeleteAlbumRequest request) {
        DeleteAlbumResponse response = new DeleteAlbumResponse();
        if (ObjectUtils.isEmpty(request.getId()))
            response.setResult(musicService.deleteAllAlbumInfo());
        else response.setResult(musicService.deleteAlbumInfo(request.getId()));
        return response;
    }
}
