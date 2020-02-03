package ru.rti.kettu.sbkotlin.client

import albumendpoint.*
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import ru.rti.kettu.sbkotlin.client.fallback.DbConnectorSoapFallback

@FeignClient(name = "soapClient", url = "http://localhost:1001/ws/music.wsdl", fallback = DbConnectorSoapFallback::class)
interface DbConnectorSoapClient {

    @PostMapping(path = [""], consumes = ["text/xml"], produces = ["text/xml"])
    fun getAlbum(@RequestBody request: GetAlbumRequest): GetAlbumResponse

    @PostMapping(path = [""], consumes = ["text/xml"], produces = ["text/xml"])
    fun createAlbum(@RequestBody request: CreateAlbumRequest): CreateAlbumResponse

    @PostMapping(path = [""], consumes = ["text/xml"], produces = ["text/xml"])
    fun updateAlbum(@RequestBody request: UpdateAlbumRequest): UpdateAlbumResponse

    @PostMapping(path = [""], consumes = ["text/xml"], produces = ["text/xml"])
    fun deleteAlbum(@RequestBody request: DeleteAlbumRequest): DeleteAlbumResponse
}