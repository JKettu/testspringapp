package ru.rti.kettu.sbkotlin.controller

import albumendpoint.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*
import ru.rti.kettu.sbkotlin.client.DbConnectorSoapClient
import ru.rti.kettu.sbkotlin.model.Album

@RestController
@RequestMapping(path = ["/soap"])
class ConsumerSoapController (@Autowired @Qualifier(value = "soapClient")  val soapClient: DbConnectorSoapClient) {

    @PostMapping(path = ["/createAlbum"])
    fun createAlbum(@RequestBody album: Album): CreateAlbumResponse? {
        val req = CreateAlbumRequest()
        req.author = album.author
        req.name = album.name
        req.year = album.year
        return soapClient.createAlbum(req)
    }

    @PostMapping(path = ["/updateAlbum"])
    fun updateAlbum(@RequestBody album: Album): UpdateAlbumResponse? {
        if (album.id == null) return null
        val req = UpdateAlbumRequest()
        req.album = albumendpoint.Album()
        req.album.id = album.id as Long
        req.album.author = album.author
        req.album.name = album.name
        req.album.year = album.year
        return soapClient.updateAlbum(req)
    }

    @PostMapping(path = ["/deleteAlbum"])
    fun deleteAlbum(@RequestParam id: Long?): DeleteAlbumResponse? {
        val req = DeleteAlbumRequest()
        req.id = id
        return soapClient.deleteAlbum(req)
    }

    @GetMapping(path = ["/getAlbum"])
    fun getAlbum(@RequestParam id: Long?): GetAlbumResponse? {
        val req = GetAlbumRequest()
        req.id = id
        return soapClient.getAlbum(req)
    }
}