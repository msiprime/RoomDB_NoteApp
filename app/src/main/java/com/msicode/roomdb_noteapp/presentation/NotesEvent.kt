package com.msicode.roomdb_noteapp.presentation

import com.msicode.roomdb_noteapp.data.Note

sealed interface NotesEvent {
    object SortNotes : NotesEvent
    data class DeleteNote(val note: Note) : NotesEvent
    data class SaveNote(
        val title: String,
        val description: String
    ) : NotesEvent
}