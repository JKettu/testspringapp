package ru.rti.kettu.dbconnectorapi.model

import ru.rti.kettu.dbconnectorapi.constants.AlbumOperations
import java.io.Serializable

class OperationAlbumApi constructor(): Serializable {

    var operation: AlbumOperations? = null
    var album: AlbumApi? = null

    constructor(operation: AlbumOperations, album: AlbumApi) : this() {
        this.operation = operation
        this.album = album
    }
}