package com.example.todo_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_app.adapters.TodoAdapter
import com.example.todo_app.databinding.FragmentTodoListBinding
import com.example.todo_app.dialogs.DeleteConfirmationDialog
import com.example.todo_app.entities.TodoModel
import com.example.todo_app.utils.todo_delete
import com.example.todo_app.utils.todo_edit
import com.example.todo_app.viewmodels.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoListFragment : Fragment() {

    private lateinit var binding: FragmentTodoListBinding
    private val todoViewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        val adapter = TodoAdapter(::todoAction)
        binding.rvTodo.layoutManager = LinearLayoutManager(activity)
        binding.rvTodo.adapter = adapter
        todoViewModel.fetchAllTodo().observe(viewLifecycleOwner) { todoList ->
            adapter.submitList(todoList)
        }

        binding.fbtAdd.setOnClickListener {
            findNavController().navigate(R.id.newTodoAction)
        }

        return binding.root
    }

    private fun todoAction(todoModel: TodoModel, tag: String) {
        when(tag) {
            todo_edit -> todoViewModel.updateTodo(todoModel)
            todo_delete -> {
                DeleteConfirmationDialog {
                    todoViewModel.deleteTodo(todoModel)
                }.show(childFragmentManager, "delete")
            }
        }
    }
}