package com.sampleapp.feature.quiz.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.sampleapp.R
import com.sampleapp.SampleApp
import com.sampleapp.databinding.ActivityQuizBinding
import com.sampleapp.feature.modules.models.Module
import com.sampleapp.feature.quiz.models.QuestionState
import com.sampleapp.feature.quiz.models.QuizUiState
import com.sampleapp.feature.quiz.viewmodel.QuizViewModel
import javax.inject.Inject

class QuizActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: QuizViewModel
    private lateinit var binding: ActivityQuizBinding
    private var canNavigate = true
    private var currentModuleId: String? = null
    private var isReviewMode = false

    companion object {
        private const val ANIMATION_DURATION = 300L
        private const val OPTION_ANIMATION_DELAY = 50L
        private const val MODULE = "module"
        private const val IS_REVIEW_MODE = "is_review_mode"
        private const val STATE_REVIEW_MODE = "state_review_mode"
        private const val STATE_CAN_NAVIGATE = "state_can_navigate"

        fun start(context: Context, module: Module, isReviewMode: Boolean = false) {
            val intent = Intent(context, QuizActivity::class.java).apply {
                putExtra(MODULE, module)
                putExtra(IS_REVIEW_MODE, isReviewMode)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBindings()
        setupDependencies()
        initializeViewModel()
        
        // Restore state or get from intent
        if (savedInstanceState != null) {
            isReviewMode = savedInstanceState.getBoolean(STATE_REVIEW_MODE, false)
            canNavigate = savedInstanceState.getBoolean(STATE_CAN_NAVIGATE, true)
        } else {
            getBundleData()
        }
        
        setupClickListeners()
        observeViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_REVIEW_MODE, isReviewMode)
        outState.putBoolean(STATE_CAN_NAVIGATE, canNavigate)
    }

    private fun initializeBindings() {
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupDependencies() {
        (application as SampleApp).appComponent.inject(this)
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[QuizViewModel::class.java]
    }

    private fun getBundleData() {
        isReviewMode = intent.getBooleanExtra(IS_REVIEW_MODE, false)
        intent.getParcelableExtra<Module?>(MODULE)?.let { module ->
            currentModuleId = module.id
            module.questionsUrl?.let { questionsUrl ->
                if (isReviewMode) {
                    viewModel.loadQuestionsForReview(questionsUrl, module.id ?: "")
                } else {
                    viewModel.loadQuestions(questionsUrl)
                }
            }
        }
    }

    private fun setupClickListeners() {
        if (!isReviewMode) {
            binding.optionsContainer.btnOption1.setOnClickListener { selectOption(index = 0) }
            binding.optionsContainer.btnOption2.setOnClickListener { selectOption(index = 1) }
            binding.optionsContainer.btnOption3.setOnClickListener { selectOption(index = 2) }
            binding.optionsContainer.btnOption4.setOnClickListener { selectOption(index = 3) }
            binding.btnSkip.setOnClickListener { viewModel.skipQuestion() }
        } else {
            binding.btnSkip.text = getString(R.string.next)
            binding.btnSkip.setOnClickListener { viewModel.nextQuestion() }
        }
    }

    private fun selectOption(index: Int) {
        if (isReviewMode) return
        if (!canNavigate) return
        canNavigate = false
        viewModel.selectAnswer(index)
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is QuizUiState.Loading -> handleLoading()
                is QuizUiState.QuizInProgress -> handleQuizInProgress(state)
                is QuizUiState.QuizCompleted -> handleQuizCompleted(state)
                is QuizUiState.Error -> handleError(state.message)
            }
        }
    }

    private fun handleLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.quizContainer.visibility = View.GONE
    }

    private fun handleQuizInProgress(state: QuizUiState.QuizInProgress) {
        binding.progressBar.visibility = View.GONE
        binding.quizContainer.visibility = View.VISIBLE
        val question = state.currentQuestion
        canNavigate = !question.isAnswered && !isReviewMode
        updateQuestionNumber(question)
        updateProgressIndicators(question.questionNumber)
        if (!isReviewMode) {
            updateStreakNotification(state.currentStreak)
        } else {
            binding.streakNotificationCard.root.visibility = View.GONE
        }
        updateQuestionText(question.question.questionText)
        updateOptions(state)
    }

    private fun handleQuizCompleted(state: QuizUiState.QuizCompleted) {
        if (isReviewMode) {
            finish()
        } else {
            val module = intent.getParcelableExtra<Module?>(MODULE)
            if (module != null) {
                ResultActivity.start(this, state.result, module)
                finish()
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            } else {
                finish()
            }
        }
    }

    private fun handleError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.quizContainer.visibility = View.VISIBLE
        binding.tvQuestion.text = getString(R.string.error_message, message)
    }

    // ========== UI Update Methods ==========

    private fun updateQuestionNumber(question: QuestionState) {
        binding.tvQuestionNumber.text =
            getString(
                R.string.question_number_format,
                question.questionNumber,
                question.totalQuestions
            )
    }

    private fun updateProgressIndicators(currentQuestion: Int) {
        val indicators = getIndicatorViews()
        indicators.forEachIndexed { index, indicator ->
            val backgroundRes = when {
                index < currentQuestion - 1 -> R.drawable.bg_progress_indicator_correct
                else -> R.drawable.bg_progress_indicator_default
            }
            indicator.setBackgroundResource(backgroundRes)
        }
    }

    private fun updateStreakNotification(currentStreak: Int) {
        val shouldShow = currentStreak >= 3 && currentStreak % 2 == 1
        binding.streakNotificationCard.root.visibility = if (shouldShow) View.VISIBLE else View.GONE
        if (shouldShow) {
            binding.streakNotificationCard.tvStreakNotification.text =
                getString(R.string.streak_notification, currentStreak)
        }
    }

    private fun updateQuestionText(questionText: String) {
        binding.tvQuestion.apply {
            alpha = 0f
            text = questionText
            animate().alpha(1f).setDuration(ANIMATION_DURATION).start()
        }
    }

    private fun updateOptions(state: QuizUiState.QuizInProgress) {
        val question = state.currentQuestion
        val options = question.question.options
        val buttons = getOptionButtons()
        buttons.forEachIndexed { index, button ->
            if (index < options.size) {
                setupOption(button, options[index], index, question)
                animateOption(button, index)
            } else {
                button.visibility = View.GONE
            }
        }
    }

    private fun setupOption(
        button: MaterialButton,
        optionText: String,
        index: Int,
        question: QuestionState
    ) {
        button.apply {
            text = optionText
            visibility = View.VISIBLE
            isEnabled = !question.isAnswered && !isReviewMode

            resetOptionStyle()

            if (question.isAnswered || isReviewMode) {
                applyAnswerStyle(index, question)
            }
        }
    }

    private fun MaterialButton.resetOptionStyle() {
        setBackgroundResource(R.drawable.bg_option_dark_default)
        setTextColor(ContextCompat.getColor(this@QuizActivity, R.color.white))
    }

    private fun MaterialButton.applyAnswerStyle(index: Int, question: QuestionState) {
        val correctIndex = question.question.correctOptionIndex

        when {
            index == correctIndex -> {
                setBackgroundResource(R.drawable.bg_option_dark_correct)
            }

            index == question.selectedAnswerIndex && question.isCorrect == false -> {
                setBackgroundResource(R.drawable.bg_option_dark_wrong)
            }
        }
        setTextColor(ContextCompat.getColor(this@QuizActivity, R.color.white))
    }

    private fun animateOption(button: MaterialButton, index: Int) {
        button.apply {
            alpha = 0f
            translationX = -50f
            animate()
                .alpha(1f)
                .translationX(0f)
                .setDuration(ANIMATION_DURATION)
                .setStartDelay(index * OPTION_ANIMATION_DELAY)
                .start()
        }
    }

    private fun getOptionButtons(): List<MaterialButton> {
        return binding.optionsContainer.run {
            listOf(btnOption1, btnOption2, btnOption3, btnOption4)
        }
    }

    private fun getIndicatorViews(): List<View> {
        return binding.progressIndicatorsRow.run {
            listOf(
                indicator1, indicator2, indicator3, indicator4, indicator5,
                indicator6, indicator7, indicator8, indicator9, indicator10
            )
        }
    }
}
