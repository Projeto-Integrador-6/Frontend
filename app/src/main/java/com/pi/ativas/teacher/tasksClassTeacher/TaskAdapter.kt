package com.pi.ativas.teacher.tasksClassTeacher

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.pi.ativas.R
import com.pi.ativas.databinding.ItemTaskBinding
import com.pi.ativas.model.Task

class TaskAdapter(
    private val list: List<Task>,
    private val onItemClickListener: ItemClickListener,
    private val tasksClassTeacherViewModel: TasksClassTeacherViewModel
) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view, tasksClassTeacherViewModel)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val classroom = list[position]
        holder.bind(classroom, onItemClickListener)
    }

    override fun getItemCount(): Int = list.size

}

class TaskViewHolder(
    private val view: View,
    private val tasksClassTeacherViewModel: TasksClassTeacherViewModel
) : RecyclerView.ViewHolder(view) {
    private var binding: ItemTaskBinding = ItemTaskBinding.bind(view)

    fun bind(task: Task, onItemClickListener: ItemClickListener) {
        view.apply {
            setOnClickListener { onItemClickListener.onClick(task) }
            with(binding) {
                tvNameTask.text = task.question
                btnMenu.setOnClickListener {
                    tasksClassTeacherViewModel.taskButtonClick(task)
                }
            }
        }
    }
}

fun interface ItemClickListener {
    fun onClick(task: Task)
}