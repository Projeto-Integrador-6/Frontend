package com.pi.ativas.teacher.createGroup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pi.ativas.R
import com.pi.ativas.databinding.ItemClassroomBinding
import com.pi.ativas.databinding.ItemStudentsBinding
import com.pi.ativas.model.Classroom
import com.pi.ativas.model.User

class StudentAdapter(
    private val list: List<User>,
    private val onItemClickListener: ItemClickListener,
    ): RecyclerView.Adapter<StudentViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_students, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val students = list[position]
        holder.bind(students, onItemClickListener)
    }

    override fun getItemCount(): Int = list.size

}

class StudentViewHolder(private val view: View): RecyclerView.ViewHolder(view){
    private var binding: ItemStudentsBinding = ItemStudentsBinding.bind(view)

    fun bind(student: User, onItemClickListener: ItemClickListener){
        view.apply {
            setOnClickListener{
                setOnClickListener{ onItemClickListener.onClick(student)} }
                with(binding){
                    tvNameStudent.text = student.name
                }

        }
    }
}

fun interface ItemClickListener{
    fun onClick(requirement: User)
}