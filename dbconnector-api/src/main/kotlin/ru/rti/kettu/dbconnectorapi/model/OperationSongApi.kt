package ru.rti.kettu.dbconnectorapi.model

import ru.rti.kettu.dbconnectorapi.constants.SongOperations
import java.io.Serializable

class OperationSongApi constructor(): Serializable {

    var operation: SongOperations? = null
    var song: SongApi? = null

    constructor(operation: SongOperations, song: SongApi): this() {
        this.operation = operation
        this.song = song
    }
}