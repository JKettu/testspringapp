package ru.rti.kettu.sbkotlin.client.fallback

import org.springframework.stereotype.Component
import ru.rti.kettu.sbkotlin.client.DbConnectorClient
import ru.rti.kettu.sbkotlin.model.Album
import ru.rti.kettu.sbkotlin.model.Song

@Component
class DbConnectorFallback: DbConnectorClient {
    override fun getAllAlbums(id: Long?): List<Album>? {
        return ArrayList<Album>()
    }

    override fun updateAlbum(id: Long, author: String?, name: String?, year: Int?): Album? {
        return null
    }

    override fun deleteAlbum(id: Long?): Boolean {
        return false
    }

    override fun createAlbum(author: String?, name: String?, year: Int?): Album? {
        return null
    }

    override fun getSong(id: String?, albumId: Long?): List<Song>? {
        return ArrayList<Song>()
    }

    override fun getAllSongs(): List<Song?>? {
        return ArrayList<Song>()
    }

    override fun updateSong(id: String, name: String?, albumId: Long?): Song? {
        return null
    }

    override fun deleteSong(id: String?): Boolean {
        return false
    }

    override fun createSong(name: String?, albumId: Long?): Song? {
       return null
    }


}