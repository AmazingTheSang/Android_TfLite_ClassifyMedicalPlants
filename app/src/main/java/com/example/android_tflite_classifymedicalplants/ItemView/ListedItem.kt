package com.example.android_tflite_classifymedicalplants.ItemView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.android_tflite_classifymedicalplants.R
import com.example.android_tflite_classifymedicalplants.databinding.ListedItemBinding

class ListedItem(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    private lateinit var _binding: ListedItemBinding
    private val binding get() = _binding

    var text: String? = null
        get() {
            return binding.tvLabel.text.toString()
        }
        set(value) {
            field = value
            binding.tvLabel.text = value
        }

    init {
        init()
    }

    private fun getViewBinding() = ListedItemBinding.inflate(LayoutInflater.from(context), this, true)

    internal fun init() {
        _binding = getViewBinding()
    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        getViewBinding()
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ListedItem, 0, 0).apply {
            try {
                text = getString(R.styleable.ListedItem_text)
            } finally {
                recycle()
            }
        }
    }
}
