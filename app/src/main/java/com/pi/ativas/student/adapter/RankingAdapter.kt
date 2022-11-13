package com.pi.ativas.student.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pi.ativas.R
import com.pi.ativas.databinding.ItemRankingBinding
import com.pi.ativas.model.RankingStudent

class RankingAdapter(
    private val list: List<RankingStudent>,
    private val onItemClickListener: ItemClickListenerRA
) : RecyclerView.Adapter<RankingStudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingStudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ranking, parent, false)
        return RankingStudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingStudentViewHolder, position: Int) {
        val rankingStudent = list[position]
        holder.bind(rankingStudent, onItemClickListener, itemCount)
    }

    override fun getItemCount(): Int = list.size

}

class RankingStudentViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private var binding: ItemRankingBinding = ItemRankingBinding .bind(view)

    fun bind(
        rankingStudent: RankingStudent,
        onItemClickListener: ItemClickListenerRA,
        itemCount: Int
    ) {
        view.apply {
            setOnClickListener { onItemClickListener.onClick(rankingStudent) }
            with(binding) {
                txtName.text = rankingStudent.student_name
                txtNumber.text = itemCount.toString()
            }
        }
    }
}

fun interface ItemClickListenerRA {
    fun onClick(rankingStudent: RankingStudent)
}
