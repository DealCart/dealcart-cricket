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
import io.dealcart.cricket.CricketGameActivity
import io.dealcart.cricket.data.LeaderboardBody
import io.dealcart.cricket.data.LeaderboardListUiData
import io.dealcart.cricket.data.LeaderboardUiData
import io.dealcart.cricket.utils.BASE_URL
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class CricketViewModel @Inject constructor() : ViewModel() {
    private val _leaderboardLiveData: MutableLiveData<LeaderboardUiData> by lazy { MutableLiveData() }
    val leaderboardLiveData: LiveData<LeaderboardUiData> = _leaderboardLiveData

    fun getLeaderboard(context: Context) {
        val url = BASE_URL + "?gameType=cricket&cityMallUserId=" + CricketGameActivity.id
        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = object : JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->


                _leaderboardLiveData.value = getData(response)

                // Handle the response data
            },
            Response.ErrorListener { error ->
                Log.e("API Error", error.toString())
                // Handle errors
            },
        ) {
        }
        requestQueue.add(stringRequest)
    }

    fun addUserCricketScore(context: Context, score: Int) {

        val postData = JSONObject()
        try {
            postData.put("cityMallUserName", CricketGameActivity.name)
            postData.put("cityMallUserId", CricketGameActivity.id)
            postData.put("score", score.toString())
            postData.put("gameType", "cricket")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val requestQueue = Volley.newRequestQueue(context)

        val params = HashMap<String, String>()
        params["cityMallUserName"] = CricketGameActivity.name
        params["cityMallUserId"] = CricketGameActivity.id
        params["score"] = score.toString()
        params["gameType"] = "cricket"

        Log.e("Data", ": $params")
        Log.e("Data", ": " + params.get("score"))
        val stringRequest = object : JsonObjectRequest(Method.POST,
            BASE_URL,
            postData,
            Response.Listener { response ->
                Log.i("API Response", response.toString())
                // Handle the response data
                getLeaderboard(context)
            },
            Response.ErrorListener { error ->
                Log.e("API Error", error.toString())
                Log.e("API Error", error.message.toString())
            }) {

            override fun getParams(): Map<String, String> {
                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    private fun getData(
        response: JSONObject
    ): LeaderboardUiData {
        try {
            var leaderboardList: ArrayList<LeaderboardBody> = arrayListOf()

            val jsonArr = response.getJSONObject("body").getJSONArray("leaderBoard")
            for (i in 0 until jsonArr.length()) {
                val jsonObject = jsonArr.getJSONObject(i)
                val leaderboardBody = LeaderboardBody(
                    jsonObject.getInt("score"),
                    jsonObject.getString("customerName"),
                    jsonObject.getInt("rank"),
                    jsonObject.getString("userType")
                )

                leaderboardList.add(leaderboardBody)
            }
            val bestScore = response.getJSONObject("body").getInt("bestScore")
            val pakScore = response.getJSONObject("body").getInt("scorePakistan")
            val indScore = response.getJSONObject("body").getInt("scoreIndia")
            var userRankScore = 0
            var userRank = 0
            val viewList = arrayListOf<LeaderboardListUiData>()
            leaderboardList.forEachIndexed { index, it ->
                if (it.customerName.isEmpty()) {
                    it.customerName = "Guest"
                }
                if (index in 0..9) {
                    viewList.add(
                        LeaderboardListUiData(
                            name = it.customerName,
                            rank = it.rank,
                            score = it.score,
                            userType = it.userType
                        )
                    )
                }

            }
            if (leaderboardList.size > 10) {
                userRankScore = leaderboardList[10].score
                userRank = leaderboardList[10].rank
            } else {
                val data =
                    leaderboardList.find { it.customerName == CricketGameActivity.name && it.score == bestScore }
                data?.let {
                    userRankScore = it.score
                    userRank = it.rank
                }
            }
            return LeaderboardUiData(
                bestScore = bestScore,
                pakScore = pakScore,
                indScore = indScore,
                userRankScore = userRankScore,
                userRank = userRank,
                leaderboardList = viewList,
            )
        } catch (e: Exception) {
            return LeaderboardUiData(
                bestScore = 0,
                pakScore = 0,
                indScore = 0,
                leaderboardList = arrayListOf(),
            )
        }
    }
}