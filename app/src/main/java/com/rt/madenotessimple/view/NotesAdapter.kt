package com.rt.madenotessimple.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rt.madenotessimple.databinding.ItemNoteLayoutBinding
import com.rt.madenotessimple.model.Note
import java.util.Random

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    class NotesViewHolder(val itemBinding: ItemNoteLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val diffUtilItemCallBack = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.title == newItem.title
                    && oldItem.content == newItem.content
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
    var differAsyncList = AsyncListDiffer(this, diffUtilItemCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            ItemNoteLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differAsyncList.currentList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = differAsyncList.currentList[position]
        holder.itemBinding.tvNoteTitle.text = currentNote.title
        holder.itemBinding.tvNoteContent.text = currentNote.content

        val random = Random()
        val color = Color.argb(
            200, random.nextInt(256), random.nextInt(256), random.nextInt(256)
        )
        holder.itemBinding.tvNoteTitle.setBackgroundColor(color)

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(currentNote)
            )
        }
    }
}