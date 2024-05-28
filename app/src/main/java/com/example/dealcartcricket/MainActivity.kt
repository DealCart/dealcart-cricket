package com.example.dealcartcricket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dealcartcricket.databinding.ActivityMainBinding
import io.dealcart.cricket.CricketGameActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startGame.setOnClickListener {
            CricketGameActivity.start(
                this,
                "1",
                "Saad Sheikh"
            )
        }
    }
}