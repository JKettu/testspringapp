package ru.rti.kettu.dbconnectorapi.model

import ru.rti.kettu.dbconnectorapi.constants.SongOperations
import java.io.Serializable

data class OperationSongApi (var operation: SongOperations, val song: SongApi): Serializable {
}