package com.msicode.roomdb_noteapp.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Note::class],
    version = 1
)
abstract class NotesDatabases : RoomDatabase() {
    abstract var dao: NoteDao
}