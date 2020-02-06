package ru.rti.kettu.sbjava.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rti.kettu.sbjava.model.db.mongoDb.Song;
import ru.rti.kettu.sbjava.service.MusicService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@RestController
@RequestMapping(path = "/song")
public class SongController {

    private final MusicService musicService;

    public SongController(MusicService musicService) {
        this.musicService = musicService;
    }

    @PostMapping(path = "/createSong")
    public Song createSong(String name, Long albumId) {
        Song song = Song.builder()
                .name(name)
                .albumId(albumId)
                .build();
        Song newSong = musicService.createSongInfo(song);
        return isEmpty(newSong) ? null : newSong;
    }

    @PostMapping(path = "/deleteSong")
    public boolean deleteSong(String id) {
        if (!isEmpty(id))
            return musicService.deleteSongInfo(id);
        else return musicService.deleteAllSongs();
    }

    @PostMapping(path = "/getSong")
    public List<Song> getSong(String id, Long albumId) {
        if (!isEmpty(id))
            return Arrays.asList(musicService.getSongById(id));
        else if (!isEmpty(albumId))
            return musicService.getSongsByAlbum(albumId);
        return null;
    }

    @PostMapping(path = "/getAllSongs")
    public List<Song> getAllSongs() {
        return musicService.getAllSongs();
    }

    @PostMapping(path = "/updateSong")
    public Song updateSong(String id, String name, Long albumId) {
        if (isEmpty(id)) return null;
        Song song = Song.builder()
                .id(id)
                .name(name)
                .albumId(albumId)
                .build();
        return musicService.updateSong(song);
    }
}
