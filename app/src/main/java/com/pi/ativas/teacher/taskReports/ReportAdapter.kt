package com.pi.ativas.teacher.taskReports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pi.ativas.R
import com.pi.ativas.databinding.ItemReportsBinding
import com.pi.ativas.databinding.ItemTaskBinding
import com.pi.ativas.model.Report
import com.pi.ativas.model.Task

class ReportAdapter(
    private val list: List<Report>,
    private val onItemClickListener: ItemClickListener,
    private val tasksReportTeacherViewModel: TasksReportTeacherViewModel
) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reports, parent, false)
        return TaskViewHolder(view, tasksReportTeacherViewModel)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val classroom = list[position]
        holder.bind(classroom, onItemClickListener)
    }

    override fun getItemCount(): Int = list.size

}

class TaskViewHolder(
    private val view: View,
    private val tasksClassTeacherViewModel: TasksReportTeacherViewModel
) : RecyclerView.ViewHolder(view) {
    private var binding: ItemReportsBinding = ItemReportsBinding.bind(view)

    fun bind(report: Report, onItemClickListener: ItemClickListener) {
        view.apply {
            setOnClickListener { onItemClickListener.onClick(report) }
            with(binding) {
                tvAnswerReport.text = report.answer
                btnMenu.setOnClickListener {
                    tasksClassTeacherViewModel.taskButtonClick(report)
                }
            }
        }
    }
}

fun interface ItemClickListener {
    fun onClick(report: Report)
}