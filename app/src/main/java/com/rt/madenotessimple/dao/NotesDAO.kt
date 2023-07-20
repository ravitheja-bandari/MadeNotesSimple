package com.rt.madenotessimple.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rt.madenotessimple.model.Note

@Dao
interface NotesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("select * from note order by id desc")
    fun fetchAllNotes(): LiveData<List<Note>>

    @Query("select * from note where title like :query or content like :query")
    fun searchNote(query: String?): LiveData<List<Note>>
}