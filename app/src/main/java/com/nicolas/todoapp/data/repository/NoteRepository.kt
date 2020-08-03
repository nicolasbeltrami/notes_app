package com.nicolas.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.nicolas.todoapp.data.NoteDao
import com.nicolas.todoapp.data.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val getAllData: LiveData<List<Note>> = noteDao.getAllData()

    suspend fun insertNote(note: Note){
        noteDao.insertNote(note)
    }

}