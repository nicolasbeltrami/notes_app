package com.nicolas.todoapp.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nicolas.todoapp.R
import com.nicolas.todoapp.data.model.Note
import com.nicolas.todoapp.data.model.Priority

class SharedViewModel(application: Application): AndroidViewModel(application) {

    val emptyData: MutableLiveData<Boolean> = MutableLiveData(true)

    fun checkIfDatabaseEmpty(note: List<Note>) {
        emptyData.value = note.isEmpty()
    }

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(p0: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long)
        {
            when(position){
                0 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.priorityHigh))}
                1 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.priorityMedium))}
                2 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.priorityLow))}
            }
        }

    }

    fun verifyData(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    fun parsePriority(priority: String): Priority {
        return when(priority){
            "Prioridad Alta" -> {
                Priority.HIGH}
            "Prioridad Media" -> {
                Priority.MEDIUM}
            "Prioridad Baja" -> {
                Priority.LOW}
            else -> Priority.LOW
        }
    }

     /*fun parsePriorityToInt(priority: Priority): Int {
        return when(priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }*/
}