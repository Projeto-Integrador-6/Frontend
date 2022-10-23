package com.pi.ativas.teacher.taskTeams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pi.ativas.R
import com.pi.ativas.databinding.ItemTeamsBinding
import com.pi.ativas.teacher.model.Team

class TeamAdapter(
    private val list: List<Team>,
    private val onItemClickListener: ItemClickListener
) : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = list[position]
        holder.bind(team, onItemClickListener)
    }

    override fun getItemCount(): Int = list.size

}

class TeamViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private var binding: ItemTeamsBinding = ItemTeamsBinding.bind(view)

    fun bind(team: Team, onItemClickListener: ItemClickListener) {
        view.apply {
            setOnClickListener { onItemClickListener.onClick(team) }
            with(binding) {
                tvNameTeam.text = team.id
            }
        }
    }
}

fun interface ItemClickListener {
    fun onClick(team: Team)
}