package com.sampleapp.feature.modules.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sampleapp.databinding.ItemModuleBinding
import com.sampleapp.feature.modules.models.Module
import com.sampleapp.feature.modules.viewModel.ModulesViewModel

class ModulesAdapter(private val mListener: ModulesListener?) :
    RecyclerView.Adapter<ModulesViewHolder>() {

    private var modulesWithProgress: List<ModulesViewModel.ModuleWithProgress> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateModules(modulesWithProgress: List<ModulesViewModel.ModuleWithProgress>) {
        this.modulesWithProgress = modulesWithProgress
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModulesViewHolder {
        val inflate =
            ItemModuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModulesViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ModulesViewHolder, position: Int) {
        val moduleWithProgress = modulesWithProgress[position]
        holder.bindData(moduleWithProgress, mListener)
    }

    override fun getItemCount(): Int = modulesWithProgress.size
}

class ModulesViewHolder(private val itemBinding: ItemModuleBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bindData(
        moduleWithProgress: ModulesViewModel.ModuleWithProgress,
        listener: ModulesListener?
    ) {
        val module = moduleWithProgress.module
        itemBinding.module = module
        val totalQuestions = moduleWithProgress.totalQuestions
        val correctAnswers = moduleWithProgress.correctAnswers
        itemBinding.tvProgressSummary.text =
            "$totalQuestions Questions | Score: $correctAnswers/$totalQuestions"

        itemBinding.btnStatus.apply {
            text = if (moduleWithProgress.isCompleted) {
                context.getString(com.sampleapp.R.string.review)
            } else {
                context.getString(com.sampleapp.R.string.start)
            }

            setBackgroundResource(
                if (moduleWithProgress.isCompleted) {
                    com.sampleapp.R.drawable.bg_status_button_review
                } else {
                    com.sampleapp.R.drawable.bg_status_button
                }
            )
        }

        itemBinding.btnStatus.setOnClickListener {
            if (moduleWithProgress.isCompleted) {
                listener?.onReviewClick(module)
            } else {
                listener?.onStartClick(module)
            }
        }
    }
}

interface ModulesListener {
    fun onStartClick(data: Module?)
    fun onReviewClick(data: Module?)
}