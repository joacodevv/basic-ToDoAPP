package com.example.todoapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), TaskAdapter.TaskListener, AddEditTaskFragment.AddEditTaskListener {

    private lateinit var binding: ActivityMainBinding
    private var dao: TaskDao? = null
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVars()
        showData()
        initListeners()
        example()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initListeners() {
        binding.fabbtn.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet(task: TaskModel? = null) {
        val bottomSheet = AddEditTaskFragment(this, task)
        bottomSheet.show(supportFragmentManager, AddEditTaskFragment.TAG)
    }

    private fun example() {
       lifecycleScope.launch(Dispatchers.IO) {
           dao?.insertTask(TaskModel(1, "EXAMPLE", "EXAMPLE"))
       }
    }

    private fun showData() {
        lifecycleScope.launch {
            dao?.getAllTasks()?.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun initVars() {
        dao = AppDatabase.getDatabase(this).taskDao()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onEditBtnClicked(task: TaskModel) {
        showBottomSheet(task)
    }

    override fun onDeleteBtnClicked(task: TaskModel) {
        lifecycleScope.launch(Dispatchers.IO) {
            dao?.deleteTask(task)
        }
    }

    override fun onSaveBtnClicked(isUpdate: Boolean, task: TaskModel) {
        lifecycleScope.launch(Dispatchers.IO) {
            if (isUpdate){
                dao?.updateTask(task)
            }else{
                dao?.insertTask(task)
            }
            dao?.insertTask(task)
        }
    }


}