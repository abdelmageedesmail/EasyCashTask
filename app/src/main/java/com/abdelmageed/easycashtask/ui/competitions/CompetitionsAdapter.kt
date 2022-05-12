package com.abdelmageed.easycashtask.ui.competitions

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdelmageed.easycashtask.R
import com.abdelmageed.easycashtask.data.locale.db.CompetitionsDatabas
import com.abdelmageed.easycashtask.data.remote.responses.competitions.Competition
import com.abdelmageed.easycashtask.databinding.ItemCompetionsBinding
import com.abdelmageed.easycashtask.uitls.ViewClick

class CompetitionsAdapter(
    val context: Context,
    val list: MutableList<Competition>,
    val viewClick: ViewClick
) : RecyclerView.Adapter<CompetitionsAdapter.MyHolder>() {

    lateinit var binding: ItemCompetionsBinding

    class MyHolder(binding: ItemCompetionsBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_competions,
            parent,
            false
        )
        val myHolder: MyHolder = MyHolder(binding)
        return myHolder
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        binding.competition = list.get(position)
        holder.itemView.setOnClickListener {
            viewClick.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}