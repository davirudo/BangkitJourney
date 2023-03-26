package com.example.catatapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.catatapp.database.Note
import com.example.catatapp.database.NoteDao
import com.example.catatapp.database.NoteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(application: Application) {

    private val mNotesDao: NoteDao
    private val executorsService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }

    fun getAllNotes(): LiveData<List<Note>> = mNotesDao.getAllNotes()

    fun insert(note: Note) {
        executorsService.execute { mNotesDao.insert(note) }
    }

    fun delete(note: Note) {
        executorsService.execute { mNotesDao.delete(note) }
    }

    fun update(note: Note) {
        executorsService.execute { mNotesDao.update(note) }
    }
}