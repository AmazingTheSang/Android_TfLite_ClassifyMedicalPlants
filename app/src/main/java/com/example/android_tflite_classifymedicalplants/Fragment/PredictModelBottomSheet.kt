package com.example.android_tflite_classifymedicalplants.Fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_tflite_classifymedicalplants.Adapter.PlantImageAdapter
import com.example.android_tflite_classifymedicalplants.Adapter.UsesAdapter
import com.example.android_tflite_classifymedicalplants.Adapter.UsesAdapterListener
import com.example.android_tflite_classifymedicalplants.MainActivity
import com.example.android_tflite_classifymedicalplants.Model.PredictModel
import com.example.android_tflite_classifymedicalplants.Model.RemedyModel
import com.example.android_tflite_classifymedicalplants.databinding.PredictBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PredictModelBottomSheet : BottomSheetDialogFragment() {
    private var _binding: PredictBottomSheetBinding? = null
    private val binding get() = _binding

    private var predictModel: PredictModel? = null

    private lateinit var usesAdapter: UsesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PredictBottomSheetBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.tvDescription?.text = predictModel?.des
        usesAdapter = UsesAdapter()
        binding?.rvUses?.adapter = usesAdapter
        usesAdapter.updateData(predictModel?.uses ?: listOf())
        binding?.tvName?.text = predictModel?.label
        binding?.imvPlant?.setImageBitmap(predictModel?.avatar)
        usesAdapter.setListener(object : UsesAdapterListener {
            override fun onClick(item: RemedyModel, position: Int) {
                val intent = Intent(context, RemedyFragment::class.java)
                startActivity(intent)
            }
        })
    }

    fun updateData(predictModel: PredictModel) {
        this.predictModel = predictModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = PredictModelBottomSheet()
    }
}
