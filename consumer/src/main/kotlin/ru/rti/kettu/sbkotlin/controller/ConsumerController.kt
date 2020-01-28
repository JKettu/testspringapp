package ru.rti.kettu.sbkotlin.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rti.kettu.sbkotlin.model.Album
import ru.rti.kettu.sbkotlin.model.Song

@RestController
@RequestMapping
class ConsumerController {

    @GetMapping(path = ["/createAlbum"])
    fun createAlbum(): String {
        return ""
    }

    @GetMapping(path = ["/deleteAlbum"])
    fun deleteAlbum(id: Long) {

    }

    @GetMapping(path = ["/getAlbum"])
    fun getAlbum(id: Long): List<Album>? {
        return null
    }

    @GetMapping(path = ["/updateAlbum"])
    fun updateAlbum(): Album? {
        return null
    }

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