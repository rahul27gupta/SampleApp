package com.sampleapp.feature.modules.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.sampleapp.R
import com.sampleapp.SampleApp
import com.sampleapp.databinding.ActivityModulesBinding
import com.sampleapp.feature.modules.models.Module
import com.sampleapp.feature.modules.ui.adapter.ModulesAdaptor
import com.sampleapp.feature.modules.ui.adapter.ModulesListener
import com.sampleapp.feature.modules.viewModel.ModulesViewModel
import com.sampleapp.feature.quiz.ui.activity.QuizActivity
import com.sampleapp.network.Resource

class ModulesActivity : AppCompatActivity(), ModulesListener {
    private lateinit var binding: ActivityModulesBinding
    private lateinit var viewModel: ModulesViewModel

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ModulesActivity::class.java).apply {
                // Add any extras or flags if needed
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_modules)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val appComponent = (application as SampleApp).appComponent
        appComponent.inject(this)
        viewModel = appComponent.injectModulesViewModel()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modules)
        setupView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.observeModules().observe(this) {
            when (it) {
                is Resource.Error -> binding.progressBar.visibility = View.GONE
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvModules.adapter =
                        ModulesAdaptor(it.data ?: arrayListOf(), this@ModulesActivity)
                }
            }
        }
    }

    private fun setupView() {
        binding.appBar.tvTitle.text = getString(R.string.modules)
        viewModel.getModules()
    }

    override fun onModulesClick(data: Module?) {
        data?.let { QuizActivity.start(binding.root.context, it) }
    }
}