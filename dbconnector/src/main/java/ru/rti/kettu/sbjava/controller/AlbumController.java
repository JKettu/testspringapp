package ru.rti.kettu.sbjava.controller;

import org.springframework.web.bind.annotation.*;
import ru.rti.kettu.sbjava.model.db.h2.Album;
import ru.rti.kettu.sbjava.service.MusicService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;
import static ru.rti.kettu.sbjava.mapping.http.AlbumHttpMapping.mapHttpRequest;

@RestController
@RequestMapping(path = "/album")
public class AlbumController {

    private final MusicService musicService;

    public AlbumController(MusicService musicService) {
        this.musicService = musicService;
    }

    @PostMapping(path = "/createAlbum")
    public Album createAlbum(@RequestParam(required = false) String author,
                              @RequestParam(required = false) String name, @RequestParam(required = false) Integer year) {
        Album album = mapHttpRequest(null, name, author , year);
        String id = musicService.createAlbumInfo(album);
        return isEmpty(id) ? null : musicService.getAlbumById(Long.parseLong(id));
    }

    @PostMapping(path = "/deleteAlbum")
    public boolean deleteAlbum(@RequestParam(required = false) Long id) {
        if (isEmpty(id)) {
            return musicService.deleteAllAlbumInfo();
        } else return musicService.deleteAlbumInfo(id);
    }

    @PostMapping(path = "/getAlbum")
    public List<Album> getAllAlbums(@RequestParam (required = false) Long id) {
        if (isEmpty(id))
            return musicService.getAllAlbums();
        else return Arrays.asList(musicService.getAlbumById(id));
    }

    @PostMapping(path = "/updateAlbum")
    public Album updateAlbum(@RequestParam Long id, @RequestParam (required = false) String author,
                             @RequestParam(required = false) String name, @RequestParam(required = false) Integer year) {
        if (id == null) return null;
        Album album = mapHttpRequest(id, name, author, year);
        return musicService.updateAlbum(album);
    }
}
