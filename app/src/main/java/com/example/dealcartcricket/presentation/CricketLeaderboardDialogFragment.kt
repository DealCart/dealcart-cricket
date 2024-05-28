package com.example.dealcartcricket.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dealcartcricket.R
import com.example.dealcartcricket.databinding.FragmentCricketLeaderboardDialogBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CricketLeaderboardDialogFragment : DialogFragment() {

    private val cricketLeaderboardDialogViewModel: CricketLeaderboardDialogViewModel by viewModels()
    private lateinit var binding: FragmentCricketLeaderboardDialogBinding

    val dialogStyle: Int = R.style.CricketLeaderboardDialogTheme

    private val args: CricketLeaderboardDialogFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, dialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCricketLeaderboardDialogBinding.inflate(inflater, container, false)

        binding.apply {
            viewModel = cricketLeaderboardDialogViewModel
            val leaderboardAdapter = CricketLeaderboardListAdapter(args.argsData.leaderboardList)
            adapter = leaderboardAdapter

            recyclerView.setOnTouchListener(object : OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    return true
                }
            })

            if (args.argsData.secondRankName.isEmpty()) {
                groupRankTwo.visibility = View.GONE
            } else {
                groupRankTwo.visibility = View.VISIBLE
            }
            if (args.argsData.thirdRankName.isEmpty()) {
                groupRankThree.visibility = View.GONE
            } else {
                groupRankThree.visibility = View.VISIBLE
            }
            if (args.argsData.leaderboardList.isEmpty()) {
                vHorizontalLine.visibility = View.GONE
            } else {
                vHorizontalLine.visibility = View.VISIBLE
            }
            if (args.argsData.userRank > 10) {
                binding.groupDot.visibility = View.VISIBLE
                binding.yourScore.visibility = View.VISIBLE
            } else {
                binding.yourScore.visibility = View.GONE
            }

            closeBtn.setOnClickListener {
                findNavController().navigateUp()
            }
            return root
        }

    }
}