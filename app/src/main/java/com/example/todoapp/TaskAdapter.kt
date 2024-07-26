package com.example.todoapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding

class TaskAdapter(private val listener: TaskListener) : ListAdapter<TaskModel, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        private var isActive = true

        init {
            binding.editBtn.setOnClickListener {
                listener.onEditBtnClicked(getItem(adapterPosition))
            }
            binding.deleteBtn.setOnClickListener() {
                isActive = !isActive
                if (isActive == false){
                    binding.itemContainer.setCardBackgroundColor(Color.GRAY)
                }else{
                    bind(getItem(adapterPosition))
                }

            }
            binding.itemContainer.setOnClickListener(object : DoubleCLickListener(){
                override fun onDoubleClick(v: View?) {
                    listener.onDoubleClicked(getItem(adapterPosition))
                }
            })
        }
        @SuppressLint("SetTextI18n")
        fun bind(task: TaskModel) {
            binding.taskNameTv.text = task.name
            binding.taskDescriptionTv.text = task.description
            when(task.priority.isNotEmpty()){
                (task.priority.lowercase() == "high") -> {
                    binding.taskPriorityTv.text = "HIGH"
                    binding.itemContainer.setCardBackgroundColor(Color.RED)
                }
                (task.priority.lowercase() == "medium") -> {
                    binding.taskPriorityTv.text = "MEDIUM"
                    binding.itemContainer.setCardBackgroundColor(Color.YELLOW)
                }
                (task.priority.lowercase() == "easy") -> {
                    binding.taskPriorityTv.text = "EASY"
                    binding.itemContainer.setCardBackgroundColor(Color.GREEN)
                }

                else -> {}
            }
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

        fun onDoubleClicked(task: TaskModel)
    }

    abstract class DoubleCLickListener : View.OnClickListener {

        private var lastClickTime: Long = 0

        companion object{

            private const val DOUBLE_CLICK_TIME_INTERVAL: Long = 3000

        }

        override fun onClick(v: View) {
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_INTERVAL) {
                onDoubleClick(v)
            }
            lastClickTime = clickTime
        }
        abstract fun onDoubleClick(v: View?)
    }


}
