package ru.rti.kettu.sbkotlin.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.rti.kettu.dbconnectorapi.model.AlbumApi
import ru.rti.kettu.dbconnectorapi.model.SongApi
import ru.rti.kettu.sbkotlin.client.fallback.DbConnectorFallback

//can not to set url if use Eureka or smth like that
@FeignClient(name = "client", url = "http://localhost:1001", fallback = DbConnectorFallback::class)
interface DbConnectorClient {
    @PostMapping(path = ["/album/getAlbum"])
    fun getAllAlbums(@RequestParam id: Long?): List<AlbumApi>?

    @PostMapping(path = ["/album/updateAlbum"])
    fun updateAlbum(@RequestParam id: Long, @RequestParam author: String?,
                    @RequestParam name: String?, @RequestParam year: Int?): AlbumApi?

    @PostMapping(path = ["/album/deleteAlbum"])
    fun deleteAlbum(@RequestParam id: Long?): Boolean

    @PostMapping(path = ["/album/createAlbum"], consumes = ["application/json"], produces = ["application/json"])
    fun createAlbum(@RequestParam author: String?, @RequestParam name: String?, @RequestParam year: Int?): AlbumApi?


    @PostMapping(path = ["/song/getSong"])
    fun getSong(@RequestParam id: String?, @RequestParam albumId: Long?): List<SongApi>?

    @PostMapping(path = ["/song/getAllSongs"])
    fun getAllSongs(): List<SongApi?>?

    @PostMapping(path = ["/song/updateSong"])
    fun updateSong(@RequestParam id: String, @RequestParam name: String?, @RequestParam albumId: Long?): SongApi?

    @PostMapping(path = ["/song/deleteSong"])
    fun deleteSong(@RequestParam id: String?): Boolean

    @PostMapping(path=["/song/createSong"], consumes = ["application/json"], produces = ["application/json"])
    fun createSong(@RequestParam name: String?, @RequestParam albumId: Long?): SongApi?
}