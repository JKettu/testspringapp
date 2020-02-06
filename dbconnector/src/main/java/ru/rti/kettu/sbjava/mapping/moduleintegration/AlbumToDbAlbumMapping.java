package ru.rti.kettu.sbjava.mapping.moduleintegration;

import ru.rti.kettu.dbconnectorapi.model.AlbumApi;
import ru.rti.kettu.dbconnectorapi.model.SongApi;
import ru.rti.kettu.sbjava.model.db.h2.Album;
import ru.rti.kettu.sbjava.model.db.mongoDb.Song;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

public class AlbumToDbAlbumMapping {

    public static Album getDbModelAlbum (AlbumApi albumApi) {
        return isEmpty(albumApi)? null :
                Album.builder()
                .id(albumApi.getId())
                .name(albumApi.getName())
                .author(albumApi.getAuthor())
                .year(albumApi.getYear())
                .build();
    }

    public static AlbumApi getApiModelAlbum (Album album) {
        return isEmpty(album) ? null : new AlbumApi(album.getId(), album.getAuthor(), album.getName(), album.getYear());
    }

    public static List<AlbumApi> getApiModelAlbumList (List<Album> album) {
        if (isEmpty(album)) return null;
        ArrayList<AlbumApi> albumList = new ArrayList<>();
        album.forEach( currentAlbum -> {
            albumList.add(new AlbumApi(currentAlbum.getId(), currentAlbum.getAuthor(),
                    currentAlbum.getName(), currentAlbum.getYear()));
        });
        return albumList;
    }
}
