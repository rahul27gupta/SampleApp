package com.sampleapp.feature.modules.ui.adapter

import SampleAppUtils.loadImage
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sampleapp.databinding.ItemModuleBinding
import com.sampleapp.feature.modules.models.Module

class ModulesAdaptor(
    private val list: ArrayList<Module>,
    private val mListener: ModulesListener?,
) :
    RecyclerView.Adapter<ModulesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModulesViewHolder {
        val inflate =
            ItemModuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModulesViewHolder(inflate)

    }

    override fun onBindViewHolder(holder: ModulesViewHolder, position: Int) {
        holder.apply { bindData(list[adapterPosition], mListener) }
    }

    override fun getItemCount(): Int = list.size
}


class ModulesViewHolder(private val itemBinding: ItemModuleBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bindData(item: Module, listener: ModulesListener?) {
        itemBinding.data = item
        itemBinding.ivIcon.loadImage(url = "usePlaceHolder")
        itemBinding.root.setOnClickListener {
            listener?.onModulesClick(item)
        }
    }
}

fun interface ModulesListener {
    fun onModulesClick(data: Module?)
}