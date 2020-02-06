package ru.rti.kettu.sbjava.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rti.kettu.sbjava.model.db.h2.Album;
import ru.rti.kettu.sbjava.model.db.mongoDb.Song;
import ru.rti.kettu.sbjava.repository.AlbumRepository;
import ru.rti.kettu.sbjava.repository.SongRepository;

import java.util.List;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class MusicService {

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    public MusicService(AlbumRepository albumRepository, SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
    }

    @Transactional
    public String createAlbumInfo(Album album) {
        if (album == null) return "";
        return albumRepository.createAlbum(album);
    }

    @Transactional
    public boolean deleteAlbumInfo(Long id) {
        if (id == null) return false;
        try {
            albumRepository.deleteAlbum(id);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Transactional
    public boolean deleteAllAlbumInfo() {
        try {
            albumRepository.deleteAllAlbums();
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Transactional
    public Album getAlbumById(Long id) {
        return albumRepository.getById(id);
    }

    @Transactional
    public List<Album> getAllAlbums() {
        return albumRepository.getAllAlbums();
    }

    @Transactional
    public Album updateAlbum(Album album) {
        if (album == null || album.getId() == null) return null;
        Album albumToUpdate = getAlbumById(album.getId());
        if(albumToUpdate == null) return null;
        if (isNotEmpty(album.getName()))
            albumToUpdate.setName(album.getName());
        if (isNotEmpty(album.getAuthor()))
            albumToUpdate.setAuthor(album.getAuthor());
        if (album.getYear() != null)
            albumToUpdate.setYear(album.getYear());
        return albumRepository.updateAlbum(albumToUpdate);
    }

    @Transactional
    public Song createSongInfo(Song song) {
        return songRepository.save(song);
    }

    @Transactional
    public boolean deleteSongInfo(String id) {
        try {
            songRepository.deleteById(id);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Transactional
    public boolean deleteAllSongs() {
        try {
            songRepository.deleteAll();
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Transactional
    public Song getSongById(String id) {
        return songRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Song> getAllSongs() {
        return (List<Song>) songRepository.findAll();
    }

    @Transactional
    public List<Song> getSongsByAlbum(Long albumId) {
        return songRepository.getSongsByAlbumId(albumId);
    }

    @Transactional
    public Song updateSong(Song song) {
        if (song == null || song.getId() == null) return null;
        Song songToUpdate = songRepository.findById(song.getId()).orElse(null);
        if (songToUpdate == null) return null;
        if (isNotEmpty(songToUpdate.getName()))
            song.setName(songToUpdate.getName());
        if (!isEmpty(songToUpdate.getAlbumId()))
            song.setAlbumId(songToUpdate.getAlbumId());
        return songRepository.save(song);
    }
}
