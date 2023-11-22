package com.example.android_tflite_classifymedicalplants.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_tflite_classifymedicalplants.ItemView.ItemConfidence
import com.example.android_tflite_classifymedicalplants.R
import com.example.android_tflite_classifymedicalplants.databinding.ItemConfidenceBinding
import  com.example.android_tflite_classifymedicalplants.Model.PredictModel

interface ConfidencesAdapterListener {
    fun onClick(item: PredictModel, position: Int)
}

class ConfidencesAdapter :
    RecyclerView.Adapter<ConfidencesAdapter.ViewHolder>() {

    private var dataSet: List<PredictModel> = listOf()

    private var listener: ConfidencesAdapterListener? = null

    fun setListener(listener: ConfidencesAdapterListener) {
        this.listener = listener
    }

    fun updateData(dataSet: List<PredictModel>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemConfidenceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener?.onClick(dataSet[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemConfidenceBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.tvLabel.text = dataSet[position].label
        val percent = dataSet[position].confidence * 100
        viewHolder.binding.tvPercent.text = "$percent%"
        if (position == 0) {
            viewHolder.binding.tvPercent.text
        }
    }

    override fun getItemCount() = dataSet.size
}
