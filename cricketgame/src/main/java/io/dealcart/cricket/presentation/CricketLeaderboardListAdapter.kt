package io.dealcart.cricket.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.dealcart.cricket.R
import io.dealcart.cricket.data.LeaderboardListUiData
import io.dealcart.cricket.databinding.CricketGameLeaderboardItemBinding

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
//            if (item.userType == "dc_user")
//                binding.ivFlag.setImageIcon(R.drawable.ic_falg_pak)
//                else binding.ivFlag.setImageDrawable(R.drawable.ic_falg_pak)
        }
    }
}