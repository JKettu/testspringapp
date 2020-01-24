package ru.rti.kettu.sbjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.rti.kettu.sbjava.model.Album;
import ru.rti.kettu.sbjava.model.Song;
import ru.rti.kettu.sbjava.repository.AlbumRepository;
import ru.rti.kettu.sbjava.repository.SongRepository;

import java.io.Serializable;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;
import static org.springframework.util.ObjectUtils.isEmpty;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    SongRepository songRepository;

    @Transactional
    public Serializable createAlbumInfo (String author, String name, int year) {
        Album album = new Album();
        album.setAuthor(author);
        album.setName(name);
        album.setYear(year);
        return albumRepository.createAlbum(album);
    }

    @Transactional
    public void deleteAlbumInfo (Long id) {
        albumRepository.deleteAlbum(id);
    }

    @Transactional
    public Album getAlbumById (Long id) {
        return albumRepository.getById(id);
    }

    @Transactional
    public List<Album> getAllAlbums () {
        return albumRepository.getAllAlbums();
    }

    @Transactional
    public Album updateAlbum(Long id, String name, String author) {
        Album album = albumRepository.getById(id);
        if (isNotEmpty(name))
            album.setName(name);
        if (isNotEmpty(author))
            album.setAuthor(author);
        return albumRepository.updateAlbum(album);
    }

    @Transactional
    public Song createSongInfo (String name, Long albumId) {
        Song song = new Song();
        song.setName(name);
        song.setAlbumId(albumId);
        return songRepository.save(song);
    }

    @Transactional
    public void deleteSongInfo (String id) {
        songRepository.deleteById(id);
    }

    @Transactional
    public Song getSongById(String id) {
        return songRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Song> getAllSongs() {
        return (List<Song>)songRepository.findAll();
    }

    @Transactional
    public List<Song> getSongsByAlbum(Long albumId) {
        return songRepository.getSongsByAlbumId(albumId);
    }

    @Transactional
    public Song updateSong(String id, String name, Long albumId) {
        Song song = songRepository.findById(id).orElse(null);
        if (song == null) return null;
        if (isNotEmpty(name))
            song.setName(name);
        if (!isEmpty(albumId))
            song.setAlbumId(albumId);
        return songRepository.save(song);
    }
}
