package com.rt.madenotessimple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.rt.madenotessimple.database.NoteDatabase
import com.rt.madenotessimple.databinding.ActivityMainBinding
import com.rt.madenotessimple.repository.NotesRepository
import com.rt.madenotessimple.viewModel.NotesViewModel
import com.rt.madenotessimple.viewModel.NotesViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var noteViewModel: NotesViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity","onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val notesRepository = NotesRepository(NoteDatabase.invoke(this))
        val notesViewModelFactory = NotesViewModelFactory(application, notesRepository)
        noteViewModel =
            ViewModelProvider(this, notesViewModelFactory).get(NotesViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity","onDestroy")

    }
}

