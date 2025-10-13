package com.sampleapp.feature.quiz.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.sampleapp.databinding.ActivityResultBinding
import com.sampleapp.feature.quiz.models.QuizResult

class ResultActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityResultBinding
    private lateinit var result: QuizResult
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        result = intent.getParcelableExtra("QUIZ_RESULT") ?: return finish()
        setupUI()
        setupClickListeners()
        startAnimations()
    }
    
    private fun setupUI() {
        binding.tvCorrectAnswers.text = result.score
        binding.tvHighestStreak.text = "${result.longestStreak}"
    }
    
    private fun setupClickListeners() {
        binding.btnRestartQuiz.setOnClickListener {
            restartQuiz()
        }
        
        binding.btnClose.setOnClickListener {
            finish()
        }
    }
    
    private fun startAnimations() {
        // Fade in main container
        binding.mainContainer.alpha = 0f
        binding.mainContainer.translationY = 50f
        binding.mainContainer.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .start()
        
        // Animate congratulations message
        binding.tvCongrats.alpha = 0f
        binding.tvCongrats.scaleX = 0.8f
        binding.tvCongrats.scaleY = 0.8f
        binding.tvCongrats.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(600)
            .setStartDelay(200)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
        
        // Animate results container
        binding.resultsContainer.alpha = 0f
        binding.resultsContainer.translationY = 30f
        binding.resultsContainer.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(400)
            .setStartDelay(500)
            .start()
        
        // Animate restart button
        binding.btnRestartQuiz.alpha = 0f
        binding.btnRestartQuiz.animate()
            .alpha(1f)
            .setDuration(400)
            .setStartDelay(800)
            .start()
    }
    
    private fun restartQuiz() {
        val intent = Intent(this, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}

