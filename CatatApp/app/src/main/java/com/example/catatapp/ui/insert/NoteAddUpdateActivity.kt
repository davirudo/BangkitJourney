package com.example.catatapp.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.catatapp.database.Note
import com.example.catatapp.repository.NoteRepository

class NoteAddUpdateActivity(application: Application): ViewModel() {

    private  val mNoteRepository: NoteRepository = NoteRepository(application)

    fun insert(note: Note) {
        mNoteRepository.insert(note)
    }

    fun update(note: Note) {
        mNoteRepository.update(note)
    }

    fun delete(note: Note) {
        mNoteRepository.delete(note)
    }
}