package com.example.todo_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todo_app.databinding.FragmentNewTodoBinding
import com.example.todo_app.dialogs.DatePickerDialogFragment
import com.example.todo_app.dialogs.TimePickerDialogFragment
import com.example.todo_app.entities.TodoModel
import com.example.todo_app.utils.getFormattedDateTime
import com.example.todo_app.utils.priority_normal
import com.example.todo_app.viewmodels.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewTodoFragment : Fragment() {
    private lateinit var binding: FragmentNewTodoBinding
    private var priority = priority_normal
    private var dateInMillis = System.currentTimeMillis()
    private var timeInMillis = System.currentTimeMillis()

    private val todoViewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTodoBinding.inflate(inflater, container, false)
        binding.rgPriority.setOnCheckedChangeListener { radioGroup, i ->
            val rb = radioGroup.findViewById<RadioButton>(i)
            priority = rb.text.toString()
        }

        binding.btDate.setOnClickListener {
            DatePickerDialogFragment {timestamp ->
                dateInMillis = timestamp
                binding.btDate.text = getFormattedDateTime(dateInMillis, "dd/MM/yyyy")
            }.show(childFragmentManager, "date_picker")
        }

        binding.btTime.setOnClickListener {
            TimePickerDialogFragment {timestamp ->
                timeInMillis = timestamp
                binding.btTime.text = getFormattedDateTime(timeInMillis, "hh:mm a")
            }.show(childFragmentManager, "time_picker")
        }

        binding.btSave.setOnClickListener {
            val todoName = binding.etInput.text.toString()
            if(todoName.isEmpty()) {
                binding.etInput.error = "Pleas provide a valid ToDo name"
                return@setOnClickListener
            }
            val todo = TodoModel(name = todoName, priority = priority, date = dateInMillis, time = timeInMillis)

            todoViewModel.insertTodo(todo)
            todoViewModel.scheduleNotification(requireContext(), todoName, 1000)

            findNavController().navigate(R.id.todo_list_return)
        }

        return binding.root
    }

}