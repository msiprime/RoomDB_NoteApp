package com.msicode.roomdb_noteapp.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Note::class],
    version = 1
)
abstract class NotesDatabases : RoomDatabase() {
    //abstract val dao: NoteDao
    abstract fun getNoteDao(): NoteDao
}