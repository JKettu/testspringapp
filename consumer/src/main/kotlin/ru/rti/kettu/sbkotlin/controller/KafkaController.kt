package ru.rti.kettu.sbkotlin.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.rti.kettu.dbconnectorapi.model.AlbumApi
import ru.rti.kettu.sbkotlin.service.KafkaService

@RestController
class KafkaController (@Autowired val service: KafkaService) {

    @RequestMapping("/sendToAlbumTopicCreate")
    fun sendMessageToAlbumQueueCreate(@RequestBody album: AlbumApi): AlbumApi? {
        val response = service.createSong(album)
        return response?.album
    }
}