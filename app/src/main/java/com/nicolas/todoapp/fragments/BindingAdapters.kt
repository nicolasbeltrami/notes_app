package com.nicolas.todoapp.fragments

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nicolas.todoapp.R
import com.nicolas.todoapp.data.model.Note
import com.nicolas.todoapp.data.model.Priority
import com.nicolas.todoapp.fragments.list.ListFragmentDirections

class BindingAdapters {

    companion object {

        @JvmStatic
        @BindingAdapter("android:navigateToAddFragment")
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("android:emptyDatabase")
        fun emptyData(view: View, emptyDatabase: MutableLiveData<Boolean>){
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @JvmStatic
        @BindingAdapter("android:parsePriorityToInt")
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when(priority) {
                Priority.HIGH -> { view.setSelection(0) }
                Priority.MEDIUM -> { view.setSelection(1) }
                Priority.LOW -> { view.setSelection(2) }
            }
        }
        @JvmStatic
        @BindingAdapter("android:parsePriorityColor")
        fun parsePriorityColor(cardView: CardView, priority: Priority){
            when(priority){
                Priority.HIGH -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.priorityHigh)) }
                Priority.MEDIUM -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.priorityMedium)) }
                Priority.LOW -> { cardView.setCardBackgroundColor(cardView.context.getColor(R.color.priorityLow)) }
            }
        }

        @JvmStatic
        @BindingAdapter("android:sendDataToUpdateFragment")
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: Note){
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }
    }
}