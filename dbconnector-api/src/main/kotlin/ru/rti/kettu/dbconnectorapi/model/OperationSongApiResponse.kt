package ru.rti.kettu.dbconnectorapi.model

import ru.rti.kettu.dbconnectorapi.constants.SongOperations
import java.io.Serializable

data class OperationSongApiResponse constructor(var operation: SongOperations): Serializable {

    var song: SongApi? = null
    var songList: List<SongApi>? = null
    var isDeleted: Boolean = false

    constructor(operation: SongOperations, song: SongApi?, songList: List<SongApi>?, isDeleted: Boolean)
            : this(operation) {
        this.song = song
        this.songList = songList
        this.isDeleted = isDeleted
    }
}