package com.rt.madenotessimple.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.rt.madenotessimple.MainActivity
import com.rt.madenotessimple.R
import com.rt.madenotessimple.databinding.FragmentUpdateNoteBinding
import com.rt.madenotessimple.model.Note
import com.rt.madenotessimple.viewModel.NotesViewModel



class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NotesViewModel
    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note

        binding.apply {
            updateNoteTitle.setText(currentNote.title)
            updateNoteContent.setText(currentNote.content)
            fabUpdateNote.setOnClickListener {
                updateNote(view, currentNote.id)
            }
        }
    }

    private fun updateNote(view: View, id: Int) {
        binding.apply {
            val title = updateNoteTitle.text.toString().trim()
            if (title.isEmpty()) {
                Toast.makeText(view.context, "Enter Note Title", Toast.LENGTH_LONG).show()
            } else {
                val note = Note(id, title, updateNoteContent.text.toString().trim())
                notesViewModel.updateNote(note)
                Toast.makeText(view.context, "Note Saved Successfully", Toast.LENGTH_LONG).show()
                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_delete_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.menu.menu_delete_note -> {
                deleteNote()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        activity?.let {
            AlertDialog.Builder(it).apply {
                setTitle("Delete Note")
                setMessage("Are you sure you want to delete the note?")
                setPositiveButton("Delete") { _, _ ->
                    notesViewModel.deleteNote(currentNote)
                    view?.findNavController()
                        ?.navigate(R.id.action_updateNoteFragment_to_homeFragment)
                }
                setNegativeButton("Cancel", null)
            }.create().show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}