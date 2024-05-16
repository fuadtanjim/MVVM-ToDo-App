package com.example.todo_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.R
import com.example.todo_app.databinding.TodoItemBinding
import com.example.todo_app.entities.TodoModel
import com.example.todo_app.utils.getFormattedDateTime
import com.example.todo_app.utils.priority_low
import com.example.todo_app.utils.priority_normal
import com.example.todo_app.utils.todo_delete
import com.example.todo_app.utils.todo_edit

class TodoAdapter(val actionCallBack: (TodoModel, String) -> Unit): ListAdapter<TodoModel, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {


    class TodoViewHolder(private val binding: TodoItemBinding, val actionCallBack: (TodoModel, String) -> Unit): RecyclerView.ViewHolder(binding.root) {
        fun bind(todoModel: TodoModel) {
            binding.tvTodoName.text = todoModel.name
            binding.tvDateTime.text = "${getFormattedDateTime(todoModel.date, "dd/MM/yyyy")} ${getFormattedDateTime(todoModel.time, "hh:mm a")}"
            binding.cbComplete.isChecked = todoModel.isCompleted
            val iconId = when (todoModel.priority) {
                priority_low -> R.drawable.baseline_bluestar_24
                priority_normal -> R.drawable.baseline_greenstar_24
                else -> R.drawable.baseline_redstar_24
            }
            binding.ivPriorityIcon.setImageResource(iconId)
            binding.cbComplete.setOnClickListener {
                actionCallBack(todoModel, todo_edit)
            }
            binding.tvMenuIcon.setOnClickListener {
                val popUpMenu = PopupMenu(it.context, it)
                val inflater = popUpMenu.menuInflater
                inflater.inflate(R.menu.todo_popup_menu, popUpMenu.menu)
                popUpMenu.show()
                popUpMenu.setOnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.item_delete -> {
                            actionCallBack(todoModel, todo_delete)
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding, actionCallBack)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoItem = getItem(position)
        holder.bind(todoItem)
    }
}

class TodoDiffCallback: DiffUtil.ItemCallback<TodoModel>() {
    override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
        return oldItem == newItem
    }

}