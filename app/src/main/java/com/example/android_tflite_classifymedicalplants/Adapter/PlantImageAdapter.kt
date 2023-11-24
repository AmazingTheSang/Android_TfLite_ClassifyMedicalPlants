package com.example.android_tflite_classifymedicalplants.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_tflite_classifymedicalplants.R
import com.example.android_tflite_classifymedicalplants.databinding.ItemPlantBinding
import com.example.android_tflite_classifymedicalplants.databinding.ItemUsesBinding
import com.squareup.picasso.Picasso

interface PlantImageAdapterListener {
    fun onClick(item: String, position: Int)
}

class PlantImageAdapter :
    RecyclerView.Adapter<PlantImageAdapter.ViewHolder>() {

    private var dataSet: List<String> = listOf()

    private var listener: PlantImageAdapterListener? = null

    fun setListener(listener: PlantImageAdapterListener) {
        this.listener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(dataSet: List<String>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemPlantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener?.onClick(dataSet[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlantBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val imgUrl: String = dataSet[position]
        if (imgUrl.isEmpty()) {
            viewHolder.binding.plantImgv.setImageResource(R.drawable.ic_launcher_foreground)
        } else {
            Picasso.get().load(imgUrl).resize(100, 100).into(viewHolder.binding.plantImgv)
        }
    }

    override fun getItemCount() = dataSet.size
}

