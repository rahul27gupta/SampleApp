package com.sampleapp.feature.quiz.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sampleapp.databinding.ActivitySplashBinding
import com.sampleapp.feature.modules.ui.activity.ModulesActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            delay(2500)
            navigateToModules()
        }
    }

    private fun navigateToModules() {
        ModulesActivity.start(binding.root.context)
        finish()
    }
}

