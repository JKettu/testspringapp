package ru.rti.kettu.dbconnectorapi.model

import ru.rti.kettu.dbconnectorapi.constants.AlbumOperations
import java.io.Serializable

data class OperationAlbumApi (var operation: AlbumOperations, val album: AlbumApi): Serializable {
}