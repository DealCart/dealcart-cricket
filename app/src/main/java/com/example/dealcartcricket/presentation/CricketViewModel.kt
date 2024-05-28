package com.example.dealcartcricket.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.dealcartcricket.CricketGameActivity
import com.example.dealcartcricket.data.LeaderboardBody
import com.example.dealcartcricket.data.LeaderboardListUiData
import com.example.dealcartcricket.data.LeaderboardUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CricketViewModel @Inject constructor() : ViewModel() {
    private val _leaderboardLiveData: MutableLiveData<LeaderboardUiData> by lazy { MutableLiveData() }
    val leaderboardLiveData: LiveData<LeaderboardUiData> = _leaderboardLiveData

    fun getLeaderboard(context: Context) {
        val leaderboardList = arrayListOf<LeaderboardBody>()
        val url =
            "https://api-dev.dealcart.io/api/consumer/circket-game-city-mall?gameType=cricket&cityMallUserId=${CricketGameActivity.id}"
        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->
                val jsonArr = response.getJSONArray("leaderboard")
                for (i in 0 until jsonArr.length()) {
                    val jsonObject = jsonArr.getJSONObject(i)
                    val leaderboardBody = LeaderboardBody(
                        jsonObject.getInt("score"),
                        jsonObject.getString("customerName"),
                        jsonObject.getInt("rank")
                    )

                    leaderboardList.add(leaderboardBody)
                }

                _leaderboardLiveData.value = getData(leaderboardList)

                // Handle the response data
            },
            Response.ErrorListener { error ->
                // Handle errors
            },
        ) {
        }
        requestQueue.add(stringRequest)
    }

    fun addUserCricketScore(context: Context, score: Int) {
        val url = "https://api-dev.dealcart.io/api/consumer/circket-game-city-mall"
        val requestQueue = Volley.newRequestQueue(context)

        val params = HashMap<String, String>()
        params["cityMallUserId"] = CricketGameActivity.id
        params["cityMallUserName"] = CricketGameActivity.name
        params["score"] = score.toString()
        params["gameType"] = "cricket"

        val stringRequest = object : JsonObjectRequest(
            Method.POST, url, JSONObject(params as Map<*, *>?),
            Response.Listener { response ->
                Log.i("API Response", response.toString())
                // Handle the response data
            },
            Response.ErrorListener { error ->
                // Handle errors
            }) {

            override fun getParams(): Map<String, String> {
                return params
            }
        }

        requestQueue.add(stringRequest)
    }

    private fun getData(
        body: List<LeaderboardBody>
    ): LeaderboardUiData {
        var userRankScore = 0
        var userRank = 0
        val leaderboardList = arrayListOf<LeaderboardListUiData>()
        body.forEachIndexed { index, it ->
            if (it.customerName == CricketGameActivity.name) {
                userRankScore = it.score
                userRank = index + 1
                it.customerName = "You"
            }
            if (it.customerName.isEmpty()) {
                it.customerName = "Guest"
            }
            if (index in 3..9) {
                leaderboardList.add(
                    LeaderboardListUiData(
                        name = it.customerName,
                        rank = it.rank,
                        score = it.score
                    )
                )
            }
        }
        return LeaderboardUiData(
            firstRankName = body.firstOrNull()?.customerName?.split(" ")?.get(0) ?: "",
            firstRankScore = body.firstOrNull()?.score ?: 0,
            secondRankName = body.getOrNull(1)?.customerName?.split(" ")?.get(0) ?: "",
            secondRankScore = body.getOrNull(1)?.score ?: 0,
            thirdRankName = body.getOrNull(2)?.customerName?.split(" ")?.get(0) ?: "",
            thirdRankScore = body.getOrNull(2)?.score ?: 0,
            userRankScore = userRankScore,
            userRank = userRank,
            leaderboardList = leaderboardList,
        )
    }
}