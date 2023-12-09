package com.msicode.roomdb_noteapp.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msicode.roomdb_noteapp.data.Note
import com.msicode.roomdb_noteapp.data.NoteDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private var dao: NoteDao
) : ViewModel() {

    private val isSortedByDateAdded = MutableStateFlow(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    private var notes = isSortedByDateAdded.flatMapLatest { sort ->
        if (sort) {
            dao.getNotesOrderedByDate()
        } else {
            dao.getNotesOrderedByTittle()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    }
    val _state = MutableStateFlow(NotesState())
    val state = combine(_state, isSortedByDateAdded, notes) { state, isSortedByDateAdded, notes ->
        state.copy(
            notes = notes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NotesState())

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }

            is NotesEvent.SaveNote -> {
                val note = Note(
                    tittle = state.value.title.value,
                    description = state.value.description.value,
                    dateAdded = System.currentTimeMillis()
                )

                viewModelScope.launch {
                    dao.upsertNote(note = note)
                }
                _state.update {
                    it.copy(
                        title = mutableStateOf(""),
                        description = mutableStateOf("")
                    )
                }
            }

            NotesEvent.SortNotes -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }
        }

    }

}