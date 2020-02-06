package ru.rti.kettu.sbkotlin.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rti.kettu.dbconnectorapi.model.AlbumApi
import ru.rti.kettu.dbconnectorapi.model.SongApi
import ru.rti.kettu.sbkotlin.service.RabbitService

@RestController
class RabbitMqController(@Autowired val service: RabbitService) {

    @RequestMapping("/sendToAlbumQueueCreate")
    fun sendMessageToAlbumQueueCreate(@RequestBody album: AlbumApi): AlbumApi? {
        val response = service.sendMessageToAlbumQueueCreate(album)
        return response?.album
    }

    @RequestMapping("/sendToSongQueueCreate")
    fun sendMessageToSongQueueCreate(@RequestBody song: SongApi): SongApi? {
        val response = service.sendMessageToSongQueueCreate(song)
        return response?.song
    }
}