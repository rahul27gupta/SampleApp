package com.sampleapp.feature.modules.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.sampleapp.R
import com.sampleapp.SampleApp
import com.sampleapp.databinding.ActivityModulesBinding
import com.sampleapp.feature.modules.viewModel.ModulesViewModel

class ModulesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModulesBinding
    private lateinit var viewModel: ModulesViewModel

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

    private fun setupObserver() {}

    private fun setupView() {}
}