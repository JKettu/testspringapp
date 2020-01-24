package ru.rti.kettu.sbjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rti.kettu.sbjava.model.Song;
import ru.rti.kettu.sbjava.service.Service;

import java.util.Arrays;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;


@RestController
@RequestMapping(path = "/song")
public class SongController {

    @Autowired
    Service service;

    @GetMapping(path = "/createSong")
    public String createSong(String name, Long albumId) {
        Song song = service.createSongInfo(name, albumId);
        return isEmpty(song) ? "Couldn't create song"
                : "New song was created with id: " + song.getId();
    }

    @GetMapping(path = "/deleteSong")
    public void deleteSong(String id) {
        if (!isEmpty(id))
            service.deleteSongInfo(id);
    }

    @GetMapping(path = "/getSong")
    public List<Song> getSong(String id, Long albumId) {
        if (!isEmpty(id))
            return Arrays.asList(service.getSongById(id));
        else if (!isEmpty(albumId))
            return service.getSongsByAlbum(albumId);
        return null;
    }

    @GetMapping(path = "/getAllSongs")
    public List<Song> getAllSongs() {
        return service.getAllSongs();
    }

    @GetMapping(path = "/updateSong")
    public Song updateSong(String id, String name, Long albumId) {
        if (isEmpty(id)) return null;
        return service.updateSong(id, name, albumId);
    }
}
