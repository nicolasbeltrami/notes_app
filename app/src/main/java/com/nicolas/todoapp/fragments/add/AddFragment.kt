package com.nicolas.todoapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nicolas.todoapp.R
import com.nicolas.todoapp.data.model.Note
import com.nicolas.todoapp.data.model.Priority
import com.nicolas.todoapp.data.viewmodel.NoteViewModel
import com.nicolas.todoapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private val noteViewModel: NoteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        // Set menu
        setHasOptionsMenu(true)

        view.spinnerPriorityAdd.onItemSelectedListener = sharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuAdd) {
            insertNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertNote() {
        val noteTitle = etAddTitle.text.toString()
        val notePriority = spinnerPriorityAdd.selectedItem.toString()
        val noteDescription = etAddDescription.text.toString()

        val validation = sharedViewModel.verifyData(noteTitle, noteDescription)

        if (validation){
            // Insert note to db
            val noteData = Note(
                0,
                noteTitle,
                sharedViewModel.parsePriority(notePriority),
                noteDescription
            )
            noteViewModel.insertNote(noteData)
            Snackbar.make(requireView(),"Nota creada exitosamente", Snackbar.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Snackbar.make(requireView(),"Debe completar todos los campos", Snackbar.LENGTH_LONG).show()
        }

    }


}