package com.rt.madenotessimple.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.rt.madenotessimple.MainActivity
import com.rt.madenotessimple.R
import com.rt.madenotessimple.databinding.FragmentNewNoteBinding
import com.rt.madenotessimple.model.Note
import com.rt.madenotessimple.viewModel.NotesViewModel

class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel = (activity as MainActivity).noteViewModel
        mView = view

        binding.fabSaveNote.setOnClickListener {
            saveNote(view)
        }
    }

    private fun saveNote(mView: View) {
        binding.apply {
            val title = noteTitle.text.toString().trim()
            val content = noteContent.text.toString().trim()
            if (title.isEmpty()) {
                Toast.makeText(mView.context, "Enter the title", Toast.LENGTH_LONG).show()
            } else {
                notesViewModel.insertNote(Note(0, title, content))
                Toast.makeText(mView.context, "Note Saved Successfully", Toast.LENGTH_LONG)
                    .show()
                mView.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_save_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuSave -> {
                saveNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}