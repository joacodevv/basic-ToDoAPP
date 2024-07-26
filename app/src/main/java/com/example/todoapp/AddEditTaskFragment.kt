package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapp.databinding.FragmentAddEditTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddEditTaskFragment(private val listener: AddEditTaskListener, private val task: TaskModel?) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddEditTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachUiListener()
        if (task != null) {
            setExistingDataOnUi(task)
        }
    }

    private fun setExistingDataOnUi(task: TaskModel){
        binding.taskNameEt.setText(task.name)
        binding.taskDescriptionEt.setText(task.description)
        binding.taskPriorityEt.setText(task.priority)
        binding.saveBtn.text = getString(R.string.update)
    }

    private fun attachUiListener() {
        binding.saveBtn.setOnClickListener {
            val name = binding.taskNameEt.text.toString()
            val priority = binding.taskPriorityEt.text.toString()
            val description = binding.taskDescriptionEt.text.toString()
            if (name.isNotEmpty() && description.isNotEmpty()) {
                val taskModel1 = TaskModel(task?.id ?: 0, name, priority ,description)
                listener.onSaveBtnClicked(true ,taskModel1)
            }
            dismiss()
        }
    }

    companion object{
        const val TAG = "AddEditTaskFragment"
    }

    interface AddEditTaskListener{
        fun onSaveBtnClicked(isUpdate: Boolean, task: TaskModel)
    }

}