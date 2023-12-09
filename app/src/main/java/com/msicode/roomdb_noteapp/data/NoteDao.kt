package com.msicode.roomdb_noteapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note ORDER BY dateAdded")
    fun getNotesOrderedByDate(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY tittle ASC")
    fun getNotesOrderedByTittle(): Flow<List<Note>>

}