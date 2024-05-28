package com.example.dealcartcricket.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dealcartcricket.R
import com.example.dealcartcricket.data.LeaderboardListUiData
import com.example.dealcartcricket.databinding.CricketGameLeaderboardItemBinding

class CricketLeaderboardListAdapter(private val filterList: List<LeaderboardListUiData>) :
    RecyclerView.Adapter<CricketLeaderboardListAdapter.LeaderboardListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LeaderboardListViewHolder {
        return LeaderboardListViewHolder(
            CricketGameLeaderboardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: LeaderboardListViewHolder, position: Int) {
        holder.onBind(filterList[position], position)
    }

    inner class LeaderboardListViewHolder(
        private val binding: CricketGameLeaderboardItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: LeaderboardListUiData, position: Int) {
            binding.model = item
            binding.tvName.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.secondary_color
                )
            )
        }
    }
}