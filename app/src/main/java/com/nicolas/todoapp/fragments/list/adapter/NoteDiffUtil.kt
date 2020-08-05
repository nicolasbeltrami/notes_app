package com.nicolas.todoapp.fragments.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.nicolas.todoapp.data.model.Note

class NoteDiffUtil(private val oldList: List<Note>, private val newList: List<Note>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id

    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

}