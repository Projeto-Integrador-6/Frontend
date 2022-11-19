package com.pi.ativas.student.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pi.ativas.R
import com.pi.ativas.databinding.ItemDisciplineBinding
import com.pi.ativas.databinding.ItemTaskBinding
import com.pi.ativas.model.Discipline
import com.pi.ativas.model.Task

class DisciplineAdapter (
    private val list: List<Discipline>,
    private val onItemClickListener: ItemClickListenerDA
) : RecyclerView.Adapter<DisciplineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisciplineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_discipline, parent, false)
        return DisciplineViewHolder(view)
    }

    override fun onBindViewHolder(holder: DisciplineViewHolder, position: Int) {
        val discipline = list[position]
        holder.bind(discipline, onItemClickListener)
    }

    override fun getItemCount(): Int = list.size

}

class DisciplineViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private var binding: ItemDisciplineBinding = ItemDisciplineBinding.bind(view)

    fun bind(discipline: Discipline, onItemClickListener: ItemClickListenerDA) {
        view.apply {
            setOnClickListener { onItemClickListener.onClick(discipline) }
            with(binding) {
                tvNameTask.text = discipline.discipline_name
                txtNumber.text = discipline.discipline_name[0].toString()
            }
        }
    }
}

fun interface ItemClickListenerDA {
    fun onClick(discipline: Discipline)
}