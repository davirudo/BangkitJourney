package com.example.notesapp.adapter

import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.entity.Note

class NoteAdapter(private  val onClickCallback: OnItemClickCallback) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    var listNotes = ArrayList<Note>()
    set(listNotes) {
        if (listNotes.size > 0) {
            this.listNotes.clear()
        }
        this.listNotes.addAll(listNotes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickCallback {
        fun OnItemClicked(selectedNote: Note?, position: Int?)
    }
}