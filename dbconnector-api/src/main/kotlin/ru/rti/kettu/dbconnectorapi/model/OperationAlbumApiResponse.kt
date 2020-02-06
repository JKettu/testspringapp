package ru.rti.kettu.dbconnectorapi.model

import ru.rti.kettu.dbconnectorapi.constants.AlbumOperations
import java.io.Serializable

data class OperationAlbumApiResponse constructor(var operation: AlbumOperations): Serializable {

    var album: AlbumApi? = null
    var albumList: List<AlbumApi>? = null
    var isDeleted: Boolean = false

    constructor(operation: AlbumOperations, album: AlbumApi?, albumList: List<AlbumApi>?, isDeleted: Boolean)
            : this(operation) {
        this.album = album
        this.albumList = albumList
        this.isDeleted = isDeleted
    }
}