package ru.rti.kettu.sbkotlin.client.fallback

import albumendpoint.*
import ru.rti.kettu.sbkotlin.client.DbConnectorSoapClient

class DbConnectorSoapFallback: DbConnectorSoapClient {
    override fun getAlbum(request: GetAlbumRequest): GetAlbumResponse {
        return GetAlbumResponse()
    }

    override fun createAlbum(request: CreateAlbumRequest): CreateAlbumResponse {
        return CreateAlbumResponse()
    }

    override fun updateAlbum(request: UpdateAlbumRequest): UpdateAlbumResponse {
        return UpdateAlbumResponse()
    }

    override fun deleteAlbum(request: DeleteAlbumRequest): DeleteAlbumResponse {
        return DeleteAlbumResponse()
    }
}