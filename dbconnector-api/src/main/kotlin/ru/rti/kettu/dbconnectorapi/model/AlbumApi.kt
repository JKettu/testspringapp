package ru.rti.kettu.dbconnectorapi.model

import java.io.Serializable

class AlbumApi constructor(): Serializable {
    var id: Long? = null
    var author: String? = null
    var name: String? = null
    var year: Int? = null

    constructor(id: Long?, author: String?, name: String?, year: Int? = 0) : this() {
        this.id = id
        this.author = author
        this.name = name
        this.year = year
    }
}