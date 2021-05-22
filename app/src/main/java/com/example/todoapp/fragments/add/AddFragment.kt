package com.example.todoapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoData
import com.example.todoapp.data.viewmodels.SharedViewModel
import com.example.todoapp.data.viewmodels.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private val mTodoViewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        // Set Menu
        setHasOptionsMenu(true)

        view.priorites_spinner.onItemSelectedListener = sharedViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTittle = title_et.text.toString()
        val mPriority = priorites_spinner.selectedItem.toString()
        val mDescription = description_et.text.toString()

        val validation = sharedViewModel.verifyDataFromUser(mTittle, mDescription)

        if (validation) {

            // Insert Data
            val newData = ToDoData(
                0,
                mTittle,
                sharedViewModel.parsePriority(mPriority),
                mDescription
            )
            mTodoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
        }

    }


}