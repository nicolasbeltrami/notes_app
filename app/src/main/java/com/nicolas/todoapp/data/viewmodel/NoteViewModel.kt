package com.nicolas.todoapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nicolas.todoapp.data.NoteDatabase
import com.nicolas.todoapp.data.model.Note
import com.nicolas.todoapp.data.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application){

    private val noteDao = NoteDatabase.getDatabase(
        application
    ).noteDao()
    private val repository: NoteRepository

    val getAllNotes: LiveData<List<Note>>

    init {
        repository = NoteRepository(noteDao)
        getAllNotes = repository.getAllData
    }

    fun insertNote(note: Note) {
        viewModelScope.launch ( Dispatchers.IO ) {
            repository.insertNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

}