package ru.rti.kettu.sbjava.controller;

import org.springframework.web.bind.annotation.GetMapping;
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

    final MusicService musicService;

    public SongController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping(path = "/createSong")
    public String createSong(String name, Long albumId) {
        Song song = musicService.createSongInfo(name, albumId);
        return isEmpty(song) ? "Couldn't create song"
                : "New song was created with id: " + song.getId();
    }

    @GetMapping(path = "/deleteSong")
    public void deleteSong(String id) {
        if (!isEmpty(id))
            musicService.deleteSongInfo(id);
    }

    @GetMapping(path = "/getSong")
    public List<Song> getSong(String id, Long albumId) {
        if (!isEmpty(id))
            return Arrays.asList(musicService.getSongById(id));
        else if (!isEmpty(albumId))
            return musicService.getSongsByAlbum(albumId);
        return null;
    }

    @GetMapping(path = "/getAllSongs")
    public List<Song> getAllSongs() {
        return musicService.getAllSongs();
    }

    @GetMapping(path = "/updateSong")
    public Song updateSong(String id, String name, Long albumId) {
        if (isEmpty(id)) return null;
        return musicService.updateSong(id, name, albumId);
    }
}
