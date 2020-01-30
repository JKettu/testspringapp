package ru.rti.kettu.sbkotlin.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.rti.kettu.sbkotlin.client.fallback.DbConnectorFallback
import ru.rti.kettu.sbkotlin.model.Album

//can not to set url if use Eureka or smth like that
@FeignClient(name ="client", url = "http://localhost:1001/album", fallback = DbConnectorFallback::class)
interface DbConnectorClient {
    @PostMapping(path = ["/getAlbum"])
    fun getAllAlbums(@RequestParam id: Long?): List<Album?>?

    @PostMapping(path = ["/updateAlbum"])
    fun updateAlbum(@RequestParam id: Long?, @RequestParam author: String?,
                    @RequestParam name: String?, @RequestParam year: Int?): Album?

    @PostMapping(path = ["/deleteAlbum"])
    fun deleteAlbum(@RequestParam id: Long?): Boolean

    @PostMapping(path = ["/createAlbum"])
    fun createAlbum(@RequestParam author: String?, @RequestParam name: String?, @RequestParam year: Int?): String
}