package ru.rti.kettu.sbjava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rti.kettu.sbjava.model.db.h2.Album;
import ru.rti.kettu.sbjava.service.MusicService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping(path = "/album")
public class AlbumController {

    final MusicService musicService;

    public AlbumController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping (path = "/createAlbum")
    public String createAlbum (String author, String name, int year) {
        return "New album was created with id: " + musicService.createAlbumInfo(author, name, year);
    }

    @GetMapping (path = "/deleteAlbum")
    public void deleteAlbum (Long id) {
        musicService.deleteAlbumInfo(id);
    }

    @GetMapping (path = "/getAlbum")
    public List<Album> getAllAlbums (Long id) {
        if (isEmpty(id))
            return musicService.getAllAlbums();
        else return Arrays.asList(musicService.getAlbumById(id));
    }

    @GetMapping(path = "/updateAlbum")
    public Album updateAlbum(Long id, String author, String name, int year) {
        return musicService.updateAlbum(id, name, author, year);
    }
}
