package com.example.cookbook

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.cookbook.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {
    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!
    private lateinit var startButton: AppCompatButton
    private lateinit var resetTimerButton: AppCompatButton
    private lateinit var stopButton: AppCompatButton
    private lateinit var timerValue: TextView
    private var isTimerRunning: Boolean = false
    private var time: Long = 0L
    private var timeRemaining: Long = 0L
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val totalTime = arguments?.getString("total_time")
        Log.e("Timer", "Cooking time: $totalTime minutes")

        if (totalTime != null) {
            time = totalTime.toLong() * 60000
        }
        timeRemaining = time
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        val rootView = binding.root

        startButton = binding.startTimerButton
        stopButton = binding.stopTimerButton
        resetTimerButton = binding.resetTimerButton
        timerValue = binding.timerValue

        val initialTime = time / 1000
        if (!isTimerRunning) {
            timerValue.text = String.format(
                "%02d:%02d:%02d",
                initialTime / 3600,
                (initialTime % 3600) / 60,
                initialTime % 60
            )
        }

        startButton.setOnClickListener {

            if (!isTimerRunning) {
                timer = object : CountDownTimer(timeRemaining, 1000L) {
                    override fun onTick(millisUntilFinished: Long) {
                        timeRemaining = millisUntilFinished
                        updateTimerText()
                    }

                    override fun onFinish() {
                        startVibrations()
                        isTimerRunning = false
                        updateTimerText()
                        timeRemaining = time
                    }
                }

                timer?.start()
                isTimerRunning = true
            } else {
                timer = object : CountDownTimer(timeRemaining, 1000L) {
                    override fun onTick(millisUntilFinished: Long) {
                        timeRemaining = millisUntilFinished
                        updateTimerText()
                    }

                    override fun onFinish() {
                        startVibrations()
                        isTimerRunning = false
                        updateTimerText()
                        timeRemaining = time
                    }
                }
                timer?.start()
            }
        }

        stopButton.setOnClickListener {
            onStop()
        }

        resetTimerButton.setOnClickListener {
            resetTimer()
        }

        return rootView
    }

    private fun startVibrations() {
        if (isTimerRunning) {
            val vibrator =
                context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(500)
            Log.d("Timer", "Phone vibrates")
        }
    }

    override fun onStop() {
        super.onStop()
        if (isTimerRunning) {
            timer?.cancel()
            timer = null
        }
    }

    private fun resetTimer() {
        if (isTimerRunning) {
            timer?.cancel()
            timeRemaining = 0L
            updateTimerText()
            timer = null
            timeRemaining = time
        }
    }

    private fun updateTimerText() {
        val timerTime = timeRemaining / 1000
        val hours = timerTime / 3600
        val minutes = (timerTime % 3600) / 60
        val seconds = timerTime % 60
        timerValue.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        isTimerRunning = false
        timeRemaining = 0L
        _binding = null
    }
}