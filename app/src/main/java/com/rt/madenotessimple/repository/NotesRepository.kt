package com.rt.madenotessimple.repository

import com.rt.madenotessimple.database.NoteDatabase
import com.rt.madenotessimple.model.Note

class NotesRepository(notesDB: NoteDatabase) {
    private val notesDAO = notesDB.getNotesDAO()

    suspend fun insertNote(note: Note) = notesDAO.insertNote(note)
    suspend fun deleteNote(note: Note) = notesDAO.deleteNote(note)
    suspend fun updateNote(note: Note) = notesDAO.updateNote(note)

    fun fetchAllNotes() = notesDAO.fetchAllNotes()
    fun searchNotes(query: String?) = notesDAO.searchNote(query)
}