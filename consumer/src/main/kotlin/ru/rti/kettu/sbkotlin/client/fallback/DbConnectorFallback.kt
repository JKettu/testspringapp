package ru.rti.kettu.sbkotlin.client.fallback

import org.springframework.stereotype.Component
import ru.rti.kettu.sbkotlin.client.DbConnectorClient
import ru.rti.kettu.sbkotlin.model.Album

@Component
class DbConnectorFallback: DbConnectorClient {
    override fun getAllAlbums(id: Long?): List<Album?>? {
        return ArrayList<Album>()
    }

    override fun updateAlbum(id: Long?, author: String?, name: String?, year: Int?): Album? {
        return null
    }

    override fun deleteAlbum(id: Long?): Boolean {
        return false
    }

    override fun createAlbum(author: String?, name: String?, year: Int?): String {
        return ""
    }

}