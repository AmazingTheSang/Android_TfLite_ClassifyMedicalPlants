package com.example.android_tflite_classifymedicalplants.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android_tflite_classifymedicalplants.ItemView.ListedItem
import com.example.android_tflite_classifymedicalplants.Model.RemedyModel
import com.example.android_tflite_classifymedicalplants.R
import com.example.android_tflite_classifymedicalplants.databinding.RemedyFragmentBinding

class RemedyFragment: Fragment(R.layout.remedy_fragment) {
    private lateinit var _binding: RemedyFragmentBinding
    private val binding get() = _binding

    private lateinit var remedyModel: RemedyModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RemedyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun updateData(remedyModel: RemedyModel) {
        this.remedyModel = remedyModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateListedView()
    }

    private fun generateListedView() {
        context?.let {
            for(plant in remedyModel.ingredients) {
                val listedItem = ListedItem(it)
                listedItem.text = plant
                binding.rvIngredient.addView(listedItem)
            }
        }
    }

    companion object {
        fun newInstance() = RemedyFragment()
    }
}
