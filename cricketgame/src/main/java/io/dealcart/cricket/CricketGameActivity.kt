package io.dealcart.cricket

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CricketGameActivity : AppCompatActivity() {

    companion object {
        var id: String = ""
        var name: String = ""
        fun start(
            activity: Activity,
            userId: String,
            userName: String
        ) {
            id = userId
            name = userName
            activity.apply {
                startActivity(Intent(activity, CricketGameActivity::class.java))
            }
        }
    }

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cricket_game)
    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.navHostFragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}