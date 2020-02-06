package ru.rti.kettu.sbkotlin.service

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.rti.kettu.dbconnectorapi.constants.AlbumOperations
import ru.rti.kettu.dbconnectorapi.constants.SongOperations
import ru.rti.kettu.dbconnectorapi.model.*

@Service
class RabbitService (@Autowired val template: AmqpTemplate) {

    fun sendMessageToAlbumQueueCreate(album: AlbumApi): OperationAlbumApiResponse? {
        val message = OperationAlbumApi(AlbumOperations.CREATE, album)
        return template.convertSendAndReceive("dbConnectorExchange","album", message) as OperationAlbumApiResponse
    }

    fun sendMessageToSongQueueCreate(song: SongApi): OperationSongApiResponse? {
        val message = OperationSongApi(SongOperations.CREATE, song)
        return template.convertSendAndReceive("dbConnectorExchange","song", message) as OperationSongApiResponse
    }
}