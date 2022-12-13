package com.johndev.notebook.common.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johndev.notebook.common.dataAccess.NoteListener
import com.johndev.notebook.common.entities.NotesEntity
import com.johndev.notebook.R
import com.johndev.notebook.databinding.ItemNoteBinding

class NoteAdapter(var notesList: MutableList<NotesEntity>, var listener: NoteListener) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>(){

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        with(holder) {
            binding.apply {
                setListener(note)
                tvTitle.text = note.title.toString().trim()
                if ((note.id % 2) == 0L) {
                    container.setBackgroundResource(R.color.noteOneColor)
                } else if ((note.id % 3) == 0L) {
                    container.setBackgroundResource(R.color.noteTwoColor)
                } else if ((note.id % 5) == 0L) {
                    container.setBackgroundResource(R.color.noteThreeColor)
                } else if ((note.id % 7) == 0L) {
                    container.setBackgroundResource(R.color.noteFourColor)
                } else if ((note.id % 8) == 0L) {
                    container.setBackgroundResource(R.color.noteFiveColor)
                } else if ((note.id % 9) == 0L) {
                    container.setBackgroundResource(R.color.noteSixColor)
                }
            }
        }
    }

    override fun getItemCount(): Int = notesList.size

    fun add(note: NotesEntity) {
        notesList.add(note)
        notifyDataSetChanged()
    }

    fun remove(note: NotesEntity) {
        notesList.remove(note)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemNoteBinding.bind(view)

        fun setListener(notes: NotesEntity){
            binding.root.setOnClickListener {
                listener.onNoteListener(notes)
            }
            binding.root.setOnLongClickListener {
                listener.onNoteLongListener(notes)
                true
            }
        }
    }
}