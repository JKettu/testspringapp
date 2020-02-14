package ru.rti.kettu.sbkotlin.service

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.kafka.requestreply.RequestReplyFuture
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFutureCallback
import org.springframework.web.context.request.async.DeferredResult
import ru.rti.kettu.dbconnectorapi.constants.AlbumOperations
import ru.rti.kettu.dbconnectorapi.model.AlbumApi
import ru.rti.kettu.dbconnectorapi.model.OperationAlbumApi
import ru.rti.kettu.dbconnectorapi.model.OperationAlbumApiResponse
import ru.rti.kettu.sbkotlin.controller.ConsumerController
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit


@Service
class KafkaService (@Autowired val tmpl: ReplyingKafkaTemplate<Int, OperationAlbumApi, OperationAlbumApiResponse>, @Autowired val controller: ConsumerController) {


    fun createSong(album: AlbumApi): OperationAlbumApiResponse? {
        val message = OperationAlbumApi(AlbumOperations.CREATE, album)
        /*tmpl.send("album",0, 0, message).get(20, TimeUnit.SECONDS)
        val albumList = controller.getAlbum(null)
        albumList?.let {
            it.forEach { record ->
                run {
                    if ( (record.author == null && album.author == null
                                || record.author!= null && record.author.equals(album.author))
                            && (record.name == null && album.name == null
                                || record.name != null && record.name.equals(album.name))
                            && (record.year == null && album.year == null
                                    || record.year != null && (record.year as Int).equals(album.year))) {
                        album.id = record.id
                        val response = OperationAlbumApiResponse(AlbumOperations.CREATE)
                        response.album = album
                        return response
                    }
                }
            }
        }*/

        val record = ProducerRecord <Int, OperationAlbumApi> ("album", 0, 0, message)
        val sendAndReceive = tmpl.sendAndReceive(record)
        sendAndReceive.addCallback(object: ListenableFutureCallback<ConsumerRecord<Int, OperationAlbumApiResponse>> {
            override fun onSuccess(result: ConsumerRecord<Int, OperationAlbumApiResponse>?) {
                val reply = result?.value()
                System.out.println("Reply: " + reply.toString())
            }

            override fun onFailure(ex: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        return null
        /* For async call
        val future = tmpl.send("album",0, 0, message)
        future.addCallback(object: ListenableFutureCallback<SendResult<Int, OperationAlbumApi>> {
            override fun onSuccess(result: SendResult<Int, OperationAlbumApi>?) {
            }

            override fun onFailure(ex: Throwable) {
            }
        })*/
    }
}