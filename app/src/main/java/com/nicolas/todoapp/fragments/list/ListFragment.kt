package com.nicolas.todoapp.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nicolas.todoapp.R
import com.nicolas.todoapp.data.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private val noteViewModel: NoteViewModel by viewModels()
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

        noteViewModel.getAllNotes.observe(viewLifecycleOwner, Observer {data ->
            adapter.setData(data)
        })

        view.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        view.listLayout.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

        // Set menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }


}