package com.rt.madenotessimple.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rt.madenotessimple.repository.NotesRepository

class NotesViewModelFactory(
    val app: Application, private val notesRepository: NotesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java))
            return NotesViewModel(app, notesRepository) as T
        throw IllegalArgumentException("Unknown ViewModel")
    }
}