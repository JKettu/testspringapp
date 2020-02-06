package ru.rti.kettu.sbkotlin.client.fallback

import org.springframework.stereotype.Component
import ru.rti.kettu.dbconnectorapi.model.AlbumApi
import ru.rti.kettu.dbconnectorapi.model.SongApi
import ru.rti.kettu.sbkotlin.client.DbConnectorClient

@Component
class DbConnectorFallback: DbConnectorClient {
    override fun getAllAlbums(id: Long?): List<AlbumApi>? {
        return ArrayList<AlbumApi>()
    }

    override fun updateAlbum(id: Long, author: String?, name: String?, year: Int?): AlbumApi? {
        return null
    }

    override fun deleteAlbum(id: Long?): Boolean {
        return false
    }

    override fun createAlbum(author: String?, name: String?, year: Int?): AlbumApi? {
        return null
    }

    override fun getSong(id: String?, albumId: Long?): List<SongApi>? {
        return ArrayList<SongApi>()
    }

    override fun getAllSongs(): List<SongApi?>? {
        return ArrayList<SongApi>()
    }

    override fun updateSong(id: String, name: String?, albumId: Long?): SongApi? {
        return null
    }

    override fun deleteSong(id: String?): Boolean {
        return false
    }

    override fun createSong(name: String?, albumId: Long?): SongApi? {
       return null
    }


}