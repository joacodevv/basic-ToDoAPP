package com.example.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding

class TaskAdapter(private val listener: TaskListener) : ListAdapter<TaskModel, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.editBtn.setOnClickListener {
                listener.onEditBtnClicked(getItem(adapterPosition))
            }
            binding.deleteBtn.setOnClickListener {
                listener.onDeleteBtnClicked(getItem(adapterPosition))
            }

        }
        fun bind(task: TaskModel) {
            binding.taskNameTv.text = task.name
            binding.taskDescriptionTv.text = task.description
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<TaskModel>(){
        override fun areItemsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
        return TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface TaskListener {
        fun onEditBtnClicked(task: TaskModel)
        fun onDeleteBtnClicked(task: TaskModel)
    }
}
