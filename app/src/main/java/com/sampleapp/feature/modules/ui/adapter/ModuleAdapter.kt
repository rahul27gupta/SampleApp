package com.sampleapp.feature.modules.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sampleapp.R
import com.sampleapp.database.entities.ModuleProgressEntity
import com.sampleapp.databinding.ItemModuleBinding
import com.sampleapp.feature.modules.models.Module

class ModuleAdapter(
    private val onModuleClick: (Module) -> Unit
) : RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder>() {

    private var modules: List<Module> = emptyList()
    private var moduleProgress: Map<String, ModuleProgressEntity> = emptyMap()

    fun updateData(modules: List<Module>, progress: Map<String, ModuleProgressEntity>) {
        this.modules = modules
        this.moduleProgress = progress
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val binding = DataBindingUtil.inflate<ItemModuleBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_module,
            parent,
            false
        )
        return ModuleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val module = modules[position]
        val progress = moduleProgress[module.id]
        holder.bind(module, progress, onModuleClick)
    }

    override fun getItemCount(): Int = modules.size

    class ModuleViewHolder(
        private val binding: ItemModuleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            module: Module,
            progress: ModuleProgressEntity?,
            onModuleClick: (Module) -> Unit
        ) {
            binding.module = module
            binding.progress = progress

            // Update status button based on completion
            updateStatusButton(module, progress)

            // Set click listener
            binding.root.setOnClickListener {
                onModuleClick(module)
            }
        }

        private fun updateStatusButton(module: Module, progress: ModuleProgressEntity?) {
            val isCompleted = progress?.isCompleted == true
            
            binding.btnStatus.apply {
                text = if (isCompleted) {
                    context.getString(R.string.review)
                } else {
                    context.getString(R.string.start)
                }
                
                setBackgroundResource(
                    if (isCompleted) {
                        R.drawable.bg_status_button_review
                    } else {
                        R.drawable.bg_status_button
                    }
                )
            }
        }
    }
}
