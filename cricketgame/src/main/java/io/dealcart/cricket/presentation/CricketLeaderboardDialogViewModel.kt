package io.dealcart.cricket.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dealcart.cricket.data.LeaderboardUiData
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