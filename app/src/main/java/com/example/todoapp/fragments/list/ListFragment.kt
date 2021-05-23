package com.example.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.data.viewmodels.SharedViewModel
import com.example.todoapp.data.viewmodels.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val adapter: ListAdapter by lazy { ListAdapter() }
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recylerview = view.recyclerView
        recylerview.adapter = adapter
        recylerview.layoutManager = LinearLayoutManager(requireActivity())

        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabase(it)
        })

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkDatabaseEmpty(data)
            adapter.setData(data)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        // Set Menu
        setHasOptionsMenu(true)

        return view
    }

    private fun showEmptyDatabase(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            view?.no_data_imageView?.visibility = View.VISIBLE
            view?.no_data_textView?.visibility = View.VISIBLE
        } else {
            view?.no_data_imageView?.visibility = View.INVISIBLE
            view?.no_data_textView?.visibility = View.INVISIBLE

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            confirmRemoveAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoveAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Succesfuly Removed Everything",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything")
        builder.setMessage("Are you sure you want to remove everything")
        builder.create().show()
    }
}