package ru.rti.kettu.sbjava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rti.kettu.sbjava.model.db.h2.Album;
import ru.rti.kettu.sbjava.service.MusicService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;
import static ru.rti.kettu.sbjava.mapping.http.AlbumHttpMapping.mapHttpRequest;

@RestController
@RequestMapping(path = "/album")
public class AlbumController {

    final MusicService musicService;

    public AlbumController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping(path = "/createAlbum")
    public String createAlbum(String author, String name, int year) {
        Album album = mapHttpRequest(null, author, name, year);
        return "New album was created with id: " + musicService.createAlbumInfo(album);
    }

    @GetMapping(path = "/deleteAlbum")
    public boolean deleteAlbum(Long id) {
        if (isEmpty(id)) {
            return musicService.deleteAlbumInfo(id);
        } else return musicService.deleteAllAlbumInfo();
    }

    @GetMapping(path = "/getAlbum")
    public List<Album> getAllAlbums(Long id) {
        if (isEmpty(id))
            return musicService.getAllAlbums();
        else return Arrays.asList(musicService.getAlbumById(id));
    }

    @GetMapping(path = "/updateAlbum")
    public Album updateAlbum(Long id, String author, String name, int year) {
        Album album = mapHttpRequest(id, name, author, year);
        return musicService.updateAlbum(album);
    }
}
