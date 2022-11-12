package com.pi.ativas.teacher.homeTeacher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pi.ativas.R
import com.pi.ativas.databinding.ItemClassroomBinding
import com.pi.ativas.model.Classroom

class ClassroomAdapter(
    private val list: List<Classroom>,
    private val onItemClickListener: ItemClickListener
) : RecyclerView.Adapter<ClassroomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassroomViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_classroom, parent, false)
        return ClassroomViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClassroomViewHolder, position: Int) {
        val classroom = list[position]
        holder.bind(classroom, onItemClickListener)
    }

    override fun getItemCount(): Int = list.size

}

class ClassroomViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private var binding: ItemClassroomBinding = ItemClassroomBinding.bind(view)

    fun bind(classroom: Classroom, onItemClickListener: ItemClickListener) {
        view.apply {
            setOnClickListener { onItemClickListener.onClick(classroom) }
            with(binding) {
                tvNameClassroom.text = classroom.name
            }
        }
    }
}

fun interface ItemClickListener {
    fun onClick(requirement: Classroom)
}