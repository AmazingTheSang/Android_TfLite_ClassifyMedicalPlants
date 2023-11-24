package com.example.android_tflite_classifymedicalplants.Fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_tflite_classifymedicalplants.Adapter.PlantImageAdapter
import com.example.android_tflite_classifymedicalplants.Adapter.UsesAdapter
import com.example.android_tflite_classifymedicalplants.Model.PredictModel
import com.example.android_tflite_classifymedicalplants.databinding.PredictBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PredictModelBottomSheet : BottomSheetDialogFragment() {
    private var _binding: PredictBottomSheetBinding? = null
    private val binding get() = _binding

    private var predictModel: PredictModel? = null

    private lateinit var plantAdapter: PlantImageAdapter
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
        updateAppearance()
        plantAdapter = PlantImageAdapter()
        plantAdapter.updateData(predictModel?.listImgUrl?.toMutableList() ?: mutableListOf())
        binding?.rcvPlantImages?.adapter = plantAdapter
        usesAdapter = UsesAdapter()
        binding?.rcvUses?.adapter = usesAdapter
        usesAdapter.updateData(predictModel?.uses ?: listOf())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(PREDICT_MODEL)) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                predictModel = it.getSerializable(PREDICT_MODEL, PredictModel::class.java)
            }
        }
    }

    private fun updateAppearance() {
        val model = predictModel ?: return
        binding?.tvDescription?.text = model.des
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PREDICT_MODEL = "PREDICT_MODEL"
        fun newInstance(predictModel: PredictModel) = PredictModelBottomSheet().apply {
            arguments = Bundle().apply { putSerializable(PREDICT_MODEL, predictModel) }
        }
    }
}
