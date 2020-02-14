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

    override fun toString(): String {
        if (album != null) {
            return StringBuilder().append((album as AlbumApi).id).append("\\n")
                    .append((album as AlbumApi).author).append("\\n")
                    .append((album as AlbumApi).name).append("\\n")
                    .append((album as AlbumApi).year)
                    .toString()
        } else if (albumList != null || (albumList as List<AlbumApi>).isEmpty()) {
            return StringBuilder().toString()
        } else if (isDeleted) {
            return StringBuilder().toString()
        }
        return ""
    }
}