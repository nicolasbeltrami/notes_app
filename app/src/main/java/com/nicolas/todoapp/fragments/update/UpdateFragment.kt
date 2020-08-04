package com.nicolas.todoapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.nicolas.todoapp.R
import com.nicolas.todoapp.data.model.Note
import com.nicolas.todoapp.data.viewmodel.NoteViewModel
import com.nicolas.todoapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        // Set menu
        setHasOptionsMenu(true)

        view.etUpdateTitle.setText(args.currentItem.title)
        view.etUpdateDescription.setText(args.currentItem.description)
        view.spinnerPriorityUpdate.setSelection(sharedViewModel.parsePriorityToInt(args.currentItem.priority))
        view.spinnerPriorityUpdate.onItemSelectedListener = sharedViewModel.listener

        return view

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuSave -> updateNote()
            R.id.menuDelete -> confirmNoteRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateNote() {
        val title = etUpdateTitle.text.toString()
        val description = etUpdateDescription.text.toString()
        val getPriority = spinnerPriorityUpdate.selectedItem.toString()

        val validation = sharedViewModel.verifyData(title,description)
        if (validation) {
            // actualizar nota
            val updatedNote = Note(
                args.currentItem.id,
                title,
                sharedViewModel.parsePriority(getPriority),
                description
            )
            noteViewModel.updateNote(updatedNote)
            Snackbar.make(requireView(),"Nota actualizada exitosamente", Snackbar.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Snackbar.make(requireView(),"Nota creada exitosamente", Snackbar.LENGTH_LONG).show()
        }
    }
    // Mostrar alert dialog
    private fun confirmNoteRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Si"){_, _ ->
            noteViewModel.deleteNote(args.currentItem)
            Snackbar.make(requireView(),"Nota eliminada", Snackbar.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_, _ ->}
        builder.setTitle("Borrar ${args.currentItem.title}")
        builder.setMessage("Seguro que quieres borrar '${args.currentItem.title}'?")
        builder.create().show()
    }
}