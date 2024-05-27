package io.dealcart.cricket.presentation

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
import dagger.hilt.android.AndroidEntryPoint
import io.dealcart.cricket.R
import io.dealcart.cricket.databinding.FragmentCricketLeaderboardDialogBinding


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

        binding.viewModel = cricketLeaderboardDialogViewModel
        val leaderboardAdapter = CricketLeaderboardListAdapter(args.argsData.leaderboardList)
        binding.adapter = leaderboardAdapter

        binding.recyclerView.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        binding.closeBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
}