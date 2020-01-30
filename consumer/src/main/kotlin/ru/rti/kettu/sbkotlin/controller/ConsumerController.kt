package ru.rti.kettu.sbkotlin.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*
import ru.rti.kettu.sbkotlin.client.DbConnectorClient
import ru.rti.kettu.sbkotlin.model.Album
import ru.rti.kettu.sbkotlin.model.Song

@RestController
@RequestMapping
class ConsumerController(@Autowired val client: DbConnectorClient) {

    //Through HTTP

    @PostMapping(path = ["/createAlbum"])
    fun createAlbum(@RequestBody album: Album): String {
        return client.createAlbum(album.author, album.name, album.year)
    }

    @PostMapping(path = ["/deleteAlbum"])
    fun deleteAlbum(@RequestParam id: Long?): Boolean {
        return client.deleteAlbum(id)
    }

    @GetMapping(path = ["/getAlbum"])
    fun getAlbum(@RequestParam id: Long?): List<Album?>? {
        return client.getAllAlbums(id)
    }

    @PostMapping(path = ["/updateAlbum"])
    fun updateAlbum(@RequestBody album: Album): Album? {
        return client.updateAlbum(album.id, album.author, album.name, album.year)
    }

    //Through SOAP

    @GetMapping(path = ["/createSong"])
    fun createSong(): String {
        return ""
    }

    @GetMapping(path = ["/deleteSong"])
    fun deleteSong() {

    }

    @GetMapping(path = ["/getSong"])
    fun getSong(): List<Song>? {
        return null
    }

    @GetMapping(path = ["/updateSong"])
    fun updateSong(): Song? {
        return null
    }
}