package com.nicolas.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.nicolas.todoapp.R
import com.nicolas.todoapp.data.model.Note
import com.nicolas.todoapp.data.viewmodel.NoteViewModel
import com.nicolas.todoapp.databinding.FragmentListBinding
import com.nicolas.todoapp.databinding.FragmentListBinding.inflate
import com.nicolas.todoapp.fragments.SharedViewModel
import com.nicolas.todoapp.fragments.list.adapter.ListAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment() {

    private val noteViewModel: NoteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    // Important to do when you use data binding
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Data binding
        _binding = inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

        // Setup recyclerView
        setupRecyclerView()

        // Observing Live Data
        noteViewModel.getAllNotes.observe(viewLifecycleOwner, Observer { data ->
            sharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        //sharedViewModel.emptyData.observe(viewLifecycleOwner, Observer {
        //    showEmptyDatabaseViews(it)
        //})

        // Set menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.rvList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 200
        }

        //Swipe to delete
        swipeToDelete(recyclerView)
    }

    /*private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            view?.imgNoData?.visibility = View.VISIBLE
            view?.tvNoData?.visibility = View.VISIBLE
        } else {
            view?.imgNoData?.visibility = View.INVISIBLE
            view?.tvNoData?.visibility = View.INVISIBLE
        }
    }*/

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                // Delete item
                noteViewModel.deleteNote(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                // Undo deleted note
                restoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: Note, position: Int){
        val snackBar = Snackbar.make(requireView(), "Nota eliminada: '${deletedItem.title}'", Snackbar.LENGTH_LONG)
        snackBar.setAction("Deshacer"){
            noteViewModel.insertNote(deletedItem)
            adapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuDeleteAll) {
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    // Mostar alert dialog
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Si") { _, _ ->
            noteViewModel.deleteAll()
            Snackbar.make(requireView(), "Todo fue eliminado", Snackbar.LENGTH_LONG).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Borrar todo")
        builder.setMessage("Seguro que quieres eliminar todo?")
        builder.create().show()
    }

    // Always use it to avoid memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}