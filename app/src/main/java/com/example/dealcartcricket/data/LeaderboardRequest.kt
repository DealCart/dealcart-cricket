package com.example.dealcartcricket.data

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class LeaderboardRequest(
    val id: Long,
    val score: Int,
    val name: String
) : Serializable
