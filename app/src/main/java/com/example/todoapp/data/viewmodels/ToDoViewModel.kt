package com.example.todoapp.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.ToDoDatabase
import com.example.todoapp.data.models.ToDoData
import com.example.todoapp.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: ToDoRepository

     val getAllData: LiveData<List<ToDoData>>

    init {
        repository = ToDoRepository(toDoDao)
        getAllData = repository.getAllData
    }

    fun insertData(ToDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(ToDoData)
        }
    }

    fun updateData(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateData(toDoData)
        }
    }

    fun deleteItem(toDoData: ToDoData){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteItem(toDoData)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

}