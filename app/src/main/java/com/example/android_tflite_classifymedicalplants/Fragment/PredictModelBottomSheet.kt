package com.example.android_tflite_classifymedicalplants.Fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.android_tflite_classifymedicalplants.Model.PredictModel
import com.example.android_tflite_classifymedicalplants.R
import com.example.android_tflite_classifymedicalplants.databinding.PredictBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PredictModelBottomSheet : BottomSheetDialogFragment() {
    private var _binding: PredictBottomSheetBinding? = null
    private val binding get() = _binding

    private var predictModel: PredictModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PredictBottomSheetBinding.inflate(
            layoutInflater,
            container,
            false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateAppearance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(PREDICT_MODEL)) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                predictModel = it.getSerializable(PREDICT_MODEL, PredictModel::class.java)
            }
        }
    }

    fun updateAppearance() {
        val model = predictModel ?: return
        binding?.tvDescription?.text = model.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PREDICT_MODEL = "PREDICT_MODEL"
        private val TAG = PredictModelBottomSheet::class.java.simpleName
        fun newInstance(predictModel: PredictModel): PredictModelBottomSheet {
            val fragment = PredictModelBottomSheet()
            val bundles = Bundle()
            bundles.putSerializable(PREDICT_MODEL, predictModel)
            fragment.arguments = bundles
            return fragment
        }
    }
}
