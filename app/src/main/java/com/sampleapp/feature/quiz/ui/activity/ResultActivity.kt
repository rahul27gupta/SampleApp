package com.sampleapp.feature.quiz.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sampleapp.SampleApp
import com.sampleapp.databinding.ActivityResultBinding
import com.sampleapp.feature.modules.models.Module
import com.sampleapp.feature.modules.repository.ModuleProgressRepository
import com.sampleapp.feature.modules.ui.activity.ModulesActivity
import com.sampleapp.feature.quiz.models.QuizResult
import com.sampleapp.Utils.SampleAppUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResultActivity : AppCompatActivity() {

    @Inject
    lateinit var moduleProgressRepository: ModuleProgressRepository

    private lateinit var binding: ActivityResultBinding
    private lateinit var result: QuizResult
    private lateinit var module: Module

    companion object {
        private const val QUIZ_RESULT = "QUIZ_RESULT"
        private const val MODULE = "MODULE"

        fun start(context: Context, result: QuizResult, module: Module) {
            val intent = Intent(context, ResultActivity::class.java).apply {
                putExtra(QUIZ_RESULT, result)
                putExtra(MODULE, module)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as SampleApp).appComponent.inject(this)
        getBundleData()
        setupUI()
        setupClickListeners()
        startAnimations()
    }

    private fun getBundleData() {
        result = intent.getParcelableExtra(QUIZ_RESULT) ?: return finish()
        module = intent.getParcelableExtra(MODULE) ?: return finish()
    }

    private fun setupUI() {
        binding.tvCorrectAnswers.text = result.score
        binding.tvHighestStreak.text = "${result.longestStreak}"
    }

    private fun setupClickListeners() {
        binding.btnRestartQuiz.setOnClickListener {
            restartQuiz()
        }

        binding.btnFinish.setOnClickListener {
            finishModule()
        }

        binding.btnClose.setOnClickListener {
            finish()
        }
    }

    private fun startAnimations() {
        SampleAppUtils.animateResultScreen(
            mainContainer = binding.mainContainer,
            congratsView = binding.tvCongrats,
            resultsContainer = binding.resultsContainer,
            restartButton = binding.btnRestartQuiz,
            finishButton = binding.btnFinish
        )
    }

    private fun restartQuiz() {
        QuizActivity.start(this, module)
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun finishModule() {
        CoroutineScope(Dispatchers.IO).launch {
            val correctAnswers = result.score.split("/").firstOrNull()?.toIntOrNull() ?: 0
            moduleProgressRepository.completeModule(module.id ?: "", correctAnswers)
            runOnUiThread {
                val intent = Intent(this@ResultActivity, ModulesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }
}

