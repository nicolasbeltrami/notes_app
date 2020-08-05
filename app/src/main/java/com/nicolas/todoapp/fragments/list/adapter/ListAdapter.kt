package com.nicolas.todoapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nicolas.todoapp.R
import com.nicolas.todoapp.data.model.Note
import com.nicolas.todoapp.data.model.Priority
import com.nicolas.todoapp.databinding.ItemNoteBinding
import kotlinx.android.synthetic.main.item_note.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.NoteViewHolder>() {

    private var dataList = emptyList<Note>()

    class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(noteData: Note){
            binding.noteData = noteData
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : NoteViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNoteBinding.inflate(layoutInflater, parent, false)
                return NoteViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val currentNote = dataList[position]
        holder.bind(currentNote)

        /*holder.itemView.itemTitle.text = dataList[position].title
        holder.itemView.itemDescription.text = dataList[position].description
        holder.itemView.itemBackground.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }

        when (dataList[position].priority) {
            Priority.HIGH -> holder.itemView.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.priorityHigh
                )
            )
            Priority.MEDIUM -> holder.itemView.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.priorityMedium
                )
            )
            Priority.LOW -> holder.itemView.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.priorityLow
                )
            )
        }*/
    }

    fun setData(noteData: List<Note>){
        this.dataList = noteData
        notifyDataSetChanged()
    }
}