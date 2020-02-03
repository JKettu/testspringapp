package ru.rti.kettu.sbkotlin.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*
import ru.rti.kettu.sbkotlin.client.DbConnectorClient
import ru.rti.kettu.sbkotlin.model.Album
import ru.rti.kettu.sbkotlin.model.Song

@RestController
@RequestMapping
class ConsumerController(@Autowired @Qualifier (value = "client") val client: DbConnectorClient) {

    @PostMapping(path = ["/createAlbum"], consumes = ["application/json"])
    fun createAlbum(@RequestBody album: Album): Album? {
        return client.createAlbum(album.author, album.name, album.year)
    }

    @PostMapping(path = ["/deleteAlbum"])
    fun deleteAlbum(@RequestParam id: Long?): Boolean {
        return client.deleteAlbum(id)
    }

    @GetMapping(path = ["/getAlbum"])
    fun getAlbum(@RequestParam id: Long?): List<Album>? {
        return client.getAllAlbums(id)
    }

    @PostMapping(path = ["/updateAlbum"], consumes = ["application/json"])
    fun updateAlbum(@RequestBody album: Album): Album? {
        if (album.id == null) return null
        return client.updateAlbum(album.id!!, album.author, album.name, album.year)
    }

    @PostMapping(path = ["/createSong"], consumes = ["application/json"])
    fun createSong(@RequestBody song: Song): Song? {
        return client.createSong(song.name, song.albumId)
    }

    @PostMapping(path = ["/deleteSong"])
    fun deleteSong(@RequestParam id: String?): Boolean {
        return client.deleteSong(id)
    }

    @GetMapping(path = ["/getSong"])
    fun getSong(@RequestParam id: String?, @RequestParam albumId: Long?): List<Song>? {
        return client.getSong(id, albumId)
    }

    @PostMapping(path = ["/updateSong"], consumes = ["application/json"])
    fun updateSong(@RequestBody song: Song): Song? {
        if (song.id == null) return null
        return client.updateSong(song.id!!, song.name, song.albumId)
    }

    @GetMapping(path = ["/getAllSongs"])
    fun getAllSongs(): List<Song?>? {
        return client.getAllSongs()
    }
}