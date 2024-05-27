package io.dealcart.cricket.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dealcart.cricket.data.LeaderboardBody
import io.dealcart.cricket.data.LeaderboardListUiData
import io.dealcart.cricket.data.LeaderboardUiData
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CricketViewModel @Inject constructor() : ViewModel() {
    private val _leaderboardLiveData: MutableLiveData<LeaderboardUiData> by lazy { MutableLiveData() }
    val leaderboardLiveData: LiveData<LeaderboardUiData> = _leaderboardLiveData

    fun getLeaderboard(context: Context) {
        val leaderboardList = arrayListOf<LeaderboardBody>()
        val url = "https://api-dev.dealcart.io/api/consumer/games?gameType=cricket"
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
                Log.e("API Error", error.toString())
                // Handle errors
            },
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] =
                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjdXN0b21lcklkIjoxMzQsInVzZXJUeXBlIjoiY29uc3VtZXIiLCJpYXQiOjE3MTY1NTczMzIsImV4cCI6MTc0ODA5MzMzMn0.zu77XkEkCpVv0AzJSiKX7jD-A3-teRUBxVe1Tf1hkS0"
                return headers
            }
        }
        requestQueue.add(stringRequest)
    }

    fun addUserCricketScore(context: Context, score: Int) {
        val url = "https://api-dev.dealcart.io/api/consumer/games"
        val requestQueue = Volley.newRequestQueue(context)

        val params = HashMap<String, String>()
        params["score"] = score.toString()
        params["gameType"] = "cricket"

        val stringRequest = object : JsonObjectRequest(
            Method.POST, url, JSONObject(params as Map<*, *>?),
            Response.Listener { response ->
                Log.i("API Response", response.toString())
                // Handle the response data
            },
            Response.ErrorListener { error ->
                Log.e("API Error", error.toString())
                // Handle errors
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] =
                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjdXN0b21lcklkIjoxMzQsInVzZXJUeXBlIjoiY29uc3VtZXIiLCJpYXQiOjE3MTY1NTczMzIsImV4cCI6MTc0ODA5MzMzMn0.zu77XkEkCpVv0AzJSiKX7jD-A3-teRUBxVe1Tf1hkS0"
                return headers
            }

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
            if (it.customerName == "Rohan") {
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

//    fun navigateToLeaderboard(data: LeaderboardUiData) {
//        navigate(
//            navigationProvider.getPushNavigator(
//                CricketGameFragmentDirections.actionCricketGameFragmentToLeaderboardDialogFragment(
//                    data
//                )
//            )
//        )
//    }
}