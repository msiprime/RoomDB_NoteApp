package com.msicode.roomdb_noteapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(

    val tittle: String,
    val description: String,
    val dateAdded: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0

)
