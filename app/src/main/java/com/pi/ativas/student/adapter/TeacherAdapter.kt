package com.pi.ativas.student.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pi.ativas.R
import com.pi.ativas.databinding.ItemTeacherBinding
import com.pi.ativas.model.Teacher


class TeacherAdapter (
    private val list: List<Teacher>,
    private val onItemClickListener: ItemClickListenerTA
) : RecyclerView.Adapter<TeacherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teacher, parent, false)
        return TeacherViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val teacher = list[position]
        holder.bind(teacher, onItemClickListener)
    }

    override fun getItemCount(): Int = list.size

}

class TeacherViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private var binding: ItemTeacherBinding = ItemTeacherBinding.bind(view)

    fun bind(teacher: Teacher, onItemClickListener: ItemClickListenerTA) {
        view.apply {
            setOnClickListener { onItemClickListener.onClick(teacher) }
            with(binding) {
                txtName.text = teacher.name
                txtDisciplineName.text = teacher.discipline_name
            }
        }
    }
}

fun interface ItemClickListenerTA {
    fun onClick(teacher: Teacher)
}