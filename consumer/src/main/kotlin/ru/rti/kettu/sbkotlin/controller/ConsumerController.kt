package ru.rti.kettu.sbkotlin.controller

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*
import ru.rti.kettu.dbconnectorapi.model.AlbumApi
import ru.rti.kettu.dbconnectorapi.model.SongApi
import ru.rti.kettu.sbkotlin.client.DbConnectorClient

@RestController
@RequestMapping
class ConsumerController(@Qualifier (value = "client") val client: DbConnectorClient) {

    @PostMapping(path = ["/createAlbum"], consumes = ["application/json"])
    fun createAlbum(@RequestBody album: AlbumApi): AlbumApi? {
        return client.createAlbum(album.author, album.name, album.year)
    }

    @PostMapping(path = ["/deleteAlbum"])
    fun deleteAlbum(@RequestParam id: Long?): Boolean {
        return client.deleteAlbum(id)
    }

    @GetMapping(path = ["/getAlbum"])
    fun getAlbum(@RequestParam id: Long?): List<AlbumApi>? {
        return client.getAllAlbums(id)
    }

    @PostMapping(path = ["/updateAlbum"], consumes = ["application/json"])
    fun updateAlbum(@RequestBody album: AlbumApi): AlbumApi? {
        if (album.id == null) return null
        return client.updateAlbum(album.id!!, album.author, album.name, album.year)
    }

    @PostMapping(path = ["/createSong"], consumes = ["application/json"])
    fun createSong(@RequestBody song: SongApi): SongApi? {
        return client.createSong(song.name, song.albumId)
    }

    @PostMapping(path = ["/deleteSong"])
    fun deleteSong(@RequestParam id: String?): Boolean {
        return client.deleteSong(id)
    }

    @GetMapping(path = ["/getSong"])
    fun getSong(@RequestParam id: String?, @RequestParam albumId: Long?): List<SongApi>? {
        return client.getSong(id, albumId)
    }

    @PostMapping(path = ["/updateSong"], consumes = ["application/json"])
    fun updateSong(@RequestBody song: SongApi): SongApi? {
        if (song.id == null) return null
        return client.updateSong(song.id!!, song.name, song.albumId)
    }

    @GetMapping(path = ["/getAllSongs"])
    fun getAllSongs(): List<SongApi?>? {
        return client.getAllSongs()
    }
}