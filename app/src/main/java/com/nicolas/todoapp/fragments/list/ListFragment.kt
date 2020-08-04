package com.nicolas.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.nicolas.todoapp.R
import com.nicolas.todoapp.data.viewmodel.NoteViewModel
import com.nicolas.todoapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private val noteViewModel: NoteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.rvList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        noteViewModel.getAllNotes.observe(viewLifecycleOwner, Observer { data ->
            sharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        sharedViewModel.emptyData.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })

        view.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // Set menu
        setHasOptionsMenu(true)

        return view
    }

    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            view?.imgNoData?.visibility = View.VISIBLE
            view?.tvNoData?.visibility = View.VISIBLE
        } else {
            view?.imgNoData?.visibility = View.INVISIBLE
            view?.tvNoData?.visibility = View.INVISIBLE
        }
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
}