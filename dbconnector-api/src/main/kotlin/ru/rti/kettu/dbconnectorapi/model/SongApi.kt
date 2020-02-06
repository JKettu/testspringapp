package ru.rti.kettu.dbconnectorapi.model

import java.io.Serializable


class SongApi constructor(): Serializable {
    var id: String? = null
    var name: String? = null
    var albumId: Long? = null

    constructor(id: String?, name: String?, albumId: Long?) : this() {
        this.id = id
        this.name = name
        this.albumId = albumId
    }
}