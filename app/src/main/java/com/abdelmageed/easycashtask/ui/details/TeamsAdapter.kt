package com.abdelmageed.easycashtask.ui.details

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdelmageed.easycashtask.R
import com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails.Season
import com.abdelmageed.easycashtask.data.remote.responses.teams.Team
import com.abdelmageed.easycashtask.databinding.ItemPrevTeamsBinding
import com.abdelmageed.easycashtask.databinding.ItemTeamsBinding
import com.abdelmageed.easycashtask.uitls.ViewClick
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.RequestBuilder
import com.squareup.picasso.Picasso


class TeamsAdapter(
    val context: Context,
    val list: MutableList<Team>,
    val viewClick: ViewClick
) : RecyclerView.Adapter<TeamsAdapter.MyHolder>() {

    lateinit var binding: ItemTeamsBinding
    lateinit var requestBuilder: RequestBuilder

    class MyHolder(binding: ItemTeamsBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_teams,
            parent,
            false
        )
        val myHolder = MyHolder(binding)
        return myHolder
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        binding.details = list.get(position)
        if (!list.get(position).crestUrl.isEmpty()) {
            if (list.get(position).crestUrl.endsWith("svg")) {
                GlideToVectorYou
                    .init()
                    .with(context)
                    .load(Uri.parse(list.get(position).crestUrl), binding.ivImage)


            } else {
                Picasso.get().load(Uri.parse(list.get(position).crestUrl))
                    .into(binding.ivImage)
            }
        }

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