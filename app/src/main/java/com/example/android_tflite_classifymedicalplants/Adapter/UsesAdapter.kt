package com.example.android_tflite_classifymedicalplants.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_tflite_classifymedicalplants.Model.PredictModel
import com.example.android_tflite_classifymedicalplants.Model.RemedyModel
import com.example.android_tflite_classifymedicalplants.R
import com.example.android_tflite_classifymedicalplants.databinding.ItemConfidenceBinding
import com.example.android_tflite_classifymedicalplants.databinding.ItemUsesBinding
import com.squareup.picasso.Picasso

interface UsesAdapterListener {
    fun onClick(item: RemedyModel, position: Int)
}

class UsesAdapter :
    RecyclerView.Adapter<UsesAdapter.ViewHolder>() {

    private var dataSet: List<RemedyModel> = listOf()

    private var listener: UsesAdapterListener? = null

    fun setListener(listener: UsesAdapterListener) {
        this.listener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(dataSet: List<RemedyModel>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemUsesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener?.onClick(dataSet[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUsesBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.tvLabel.text = dataSet[position].name
    }

    override fun getItemCount() = dataSet.size
}
