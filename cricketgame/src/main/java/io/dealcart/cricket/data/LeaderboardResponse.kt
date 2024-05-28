package io.dealcart.cricket.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
data class LeaderboardResponse(
    val leaderboard: List<LeaderboardBody>?,
)

@Keep
data class LeaderboardBody(
    val score: Int,
    var customerName: String,
    val rank: Int,
    val userType: String,
)

@Keep
@Parcelize
data class LeaderboardUiData(
    val bestScore: Int = 0,
    val pakScore: Int = 0,
    val indScore: Int = 0,
    val userRankScore: Int = 0,
    val userRank: Int = 0,
    val leaderboardList: List<LeaderboardListUiData>,
) : Parcelable

@Keep
@Parcelize
data class LeaderboardListUiData(
    val rank: Int = 0,
    val name: String,
    val score: Int = 0,
    val userType: String
) : Parcelable