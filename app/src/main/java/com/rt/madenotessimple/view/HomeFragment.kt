package com.rt.madenotessimple.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rt.madenotessimple.MainActivity
import com.rt.madenotessimple.R
import com.rt.madenotessimple.databinding.FragmentNotesLayoutBinding
import com.rt.madenotessimple.model.Note
import com.rt.madenotessimple.viewModel.NotesViewModel


class HomeFragment : Fragment(R.layout.fragment_notes_layout), SearchView.OnQueryTextListener {

    private var _binding: FragmentNotesLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNotesLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel

        setUpRecyclerView()

        binding.fabNewNote.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }

    private fun setUpRecyclerView() {
        notesAdapter = NotesAdapter()
        binding.notesRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = notesAdapter
        }
        activity?.let {
            notesViewModel.getAllNotes().observe(
                viewLifecycleOwner
            ) {
                notesAdapter.differAsyncList.submitList(it)
                updateUI(it)
            }
        }

    }

    private fun updateUI(notes: List<Note>) {
        if (notes.isEmpty()) {
            binding.noNotesCard.visibility = View.VISIBLE
            binding.notesRecyclerView.visibility = View.GONE
        } else {
            binding.noNotesCard.visibility = View.GONE
            binding.notesRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_home, menu)
        val searchMenu = menu.findItem(R.id.search).actionView as SearchView
        searchMenu.isSubmitButtonEnabled = true
        searchMenu.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchNote(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null)
            searchNote(newText)
        return true
    }

    private fun searchNote(searchString: String?) {
        notesViewModel.searchNote("%$searchString%").observe(
            this
        ) {
            notesAdapter.differAsyncList.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}