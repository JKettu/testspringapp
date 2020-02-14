package ru.rti.kettu.sbjava.brokerreceiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.rti.kettu.dbconnectorapi.constants.AlbumOperations;
import ru.rti.kettu.dbconnectorapi.constants.SongOperations;
import ru.rti.kettu.dbconnectorapi.model.OperationAlbumApi;
import ru.rti.kettu.dbconnectorapi.model.OperationAlbumApiResponse;
import ru.rti.kettu.dbconnectorapi.model.OperationSongApi;
import ru.rti.kettu.dbconnectorapi.model.OperationSongApiResponse;
import ru.rti.kettu.sbjava.model.db.h2.Album;
import ru.rti.kettu.sbjava.model.db.mongoDb.Song;
import ru.rti.kettu.sbjava.service.MusicService;

import static ru.rti.kettu.sbjava.mapping.moduleintegration.AlbumToDbAlbumMapping.*;
import static ru.rti.kettu.sbjava.mapping.moduleintegration.SongToDbSongMapping.*;

@Component
public class RabbitMqListener {

    final MusicService musicService;

    public RabbitMqListener(MusicService musicService) {
        this.musicService = musicService;
    }

    @RabbitListener(queues = "albumQueue")
    public OperationAlbumApiResponse getAlbumQueueObject(OperationAlbumApi albumApi) {
        if (albumApi == null) return null;
        Album album = getDbModelAlbum(albumApi.getAlbum());
        if (album == null) return null;
        try {
            if (albumApi.getOperation().equals(AlbumOperations.CREATE)) {
                OperationAlbumApiResponse response = new OperationAlbumApiResponse(AlbumOperations.CREATE);
                String id = musicService.createAlbumInfo(album);
                if (StringUtils.isEmpty(id))
                    return null;
                album.setId(Long.parseLong(id));
                response.setAlbum(getApiModelAlbum(album));
                return response;
            }

            if (albumApi.getOperation().equals(AlbumOperations.UPDATE)) {
                OperationAlbumApiResponse response = new OperationAlbumApiResponse(AlbumOperations.UPDATE);
                response.setAlbum(getApiModelAlbum(musicService.updateAlbum(album)));
                return response;
            }

            if (albumApi.getOperation().equals(AlbumOperations.DELETE)) {
                OperationAlbumApiResponse response = new OperationAlbumApiResponse(AlbumOperations.DELETE);
                if (album.getId() == null)
                    response.setDeleted(musicService.deleteAllAlbumInfo());
                else response.setDeleted(musicService.deleteAlbumInfo(album.getId()));
                return response;
            }
            if (albumApi.getOperation().equals(AlbumOperations.GET)) {
                OperationAlbumApiResponse response = new OperationAlbumApiResponse(AlbumOperations.GET);
                if (album.getId() == null)
                    response.setAlbumList(getApiModelAlbumList(musicService.getAllAlbums()));
                else response.setAlbum(getApiModelAlbum(musicService.getAlbumById(album.getId())));
                return response;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @RabbitListener(queues = "songQueue")
    public OperationSongApiResponse getSongQueueObject(OperationSongApi songApi) {
        if (songApi == null || songApi.getOperation() == null) return null;
        Song song = getDbModelSong(songApi.getSong());
        try {
            if (songApi.getOperation().equals(SongOperations.CREATE)) {
                OperationSongApiResponse response = new OperationSongApiResponse(SongOperations.CREATE);
                response.setSong(getApiModelSong(musicService.createSongInfo(song)));
                return response;
            }

            if (songApi.getOperation().equals(SongOperations.UPDATE)) {
                OperationSongApiResponse response = new OperationSongApiResponse(SongOperations.CREATE);
                response.setSong(getApiModelSong(musicService.updateSong(song)));
                return response;
            }

            if (songApi.getOperation().equals(SongOperations.DELETE)) {
                OperationSongApiResponse response = new OperationSongApiResponse(SongOperations.DELETE);
                if (song.getId() == null)
                    response.setDeleted(musicService.deleteAllSongs());
                else response.setDeleted(musicService.deleteSongInfo(song.getId()));
                return response;
            }
            if (songApi.getOperation().equals(SongOperations.GET)) {
                OperationSongApiResponse response = new OperationSongApiResponse(SongOperations.GET);
                if (song.getId() != null)
                    response.setSong(getApiModelSong(musicService.getSongById(song.getId())));
                else if (song.getAlbumId() != null)
                    response.setSongList(getApiModelSongList(musicService.getSongsByAlbum(song.getAlbumId())));
                else response.setSongList(getApiModelSongList(musicService.getAllSongs()));
                return response;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
