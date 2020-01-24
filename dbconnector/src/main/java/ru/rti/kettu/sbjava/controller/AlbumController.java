package ru.rti.kettu.sbjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rti.kettu.sbjava.model.Album;
import ru.rti.kettu.sbjava.service.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping(path = "/album")
public class AlbumController {

    @Autowired
    Service service;

    @GetMapping (path = "/createAlbum")
    public String createAlbum (String author, String name, int year) {
        return "New album was created with id: " + service.createAlbumInfo(author, name, year);
    }

    @GetMapping (path = "/deleteAlbum")
    public void deleteAlbum (Long id) {
        service.deleteAlbumInfo(id);
    }

    @GetMapping (path = "/getAlbum")
    public List<Album> getAllAlbums (Long id) {
        if (isEmpty(id))
            return service.getAllAlbums();
        else return Arrays.asList(service.getAlbumById(id));
    }

    @GetMapping(path = "/updateAlbum")
    public Album updateAlbum(Long id, String author, String name) {
        return service.updateAlbum(id, name, author);
    }
}
