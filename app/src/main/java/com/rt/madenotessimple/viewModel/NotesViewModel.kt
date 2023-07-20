package com.rt.madenotessimple.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rt.madenotessimple.model.Note
import com.rt.madenotessimple.repository.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(app: Application, private val notesRepository: NotesRepository) :
    AndroidViewModel(app) {

    fun insertNote(note: Note) = viewModelScope.launch {
        notesRepository.insertNote(note)
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch { notesRepository.deleteNote(note = note) }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            notesRepository.updateNote(note)
        }
    }

    fun getAllNotes() = notesRepository.fetchAllNotes()

    fun searchNote(searchString: String?) = notesRepository.searchNotes(searchString)

}