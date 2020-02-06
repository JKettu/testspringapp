package ru.rti.kettu.sbjava.mapping.moduleintegration;

import ru.rti.kettu.dbconnectorapi.model.SongApi;
import ru.rti.kettu.sbjava.model.db.mongoDb.Song;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

public class SongToDbSongMapping {

    public static Song getDbModelSong (SongApi songApi) {
        return isEmpty(songApi)? null :
                Song.builder()
                .id(songApi.getId())
                .name(songApi.getName())
                .albumId(songApi.getAlbumId())
                .build();
    }

    public static SongApi getApiModelSong (Song song) {
        return isEmpty(song)? null : new SongApi(song.getId(), song.getName(), song.getAlbumId());
    }

    public static List<SongApi> getApiModelSongList (List<Song> song) {
        if (isEmpty(song)) return null;
        ArrayList<SongApi> songList = new ArrayList<>();
                song.forEach( currentSong -> {
                    songList.add( new SongApi(currentSong.getId(), currentSong.getName(), currentSong.getAlbumId()));
                });
        return songList;
    }
}
