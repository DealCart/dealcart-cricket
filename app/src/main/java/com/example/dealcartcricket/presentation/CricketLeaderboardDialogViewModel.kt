package com.example.dealcartcricket.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.dealcartcricket.data.LeaderboardUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CricketLeaderboardDialogViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val argsData: LeaderboardUiData by lazy {
        savedStateHandle.get<LeaderboardUiData>("argsData")
            ?: throw IllegalAccessException("${LeaderboardUiData::class.java.simpleName} must not be null")
    }
}