package com.example.android_tflite_classifymedicalplants.Fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.android_tflite_classifymedicalplants.Adapter.UsesAdapter
import com.example.android_tflite_classifymedicalplants.Adapter.UsesAdapterListener
import com.example.android_tflite_classifymedicalplants.Model.PredictModel
import com.example.android_tflite_classifymedicalplants.Model.RemedyModel
import com.example.android_tflite_classifymedicalplants.R
import com.example.android_tflite_classifymedicalplants.databinding.PlantDetailFragmentBinding

class PlantDetailFragment : Fragment(R.layout.plant_detail_fragment) {
    private var _binding: PlantDetailFragmentBinding? = null
    private val binding get() = _binding

    private var predictModel: PredictModel? = null

    private lateinit var usesAdapter: UsesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PlantDetailFragmentBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configRvUse()
        updateStrings()
        setPlantImage()
        setListener()
    }

    private fun configRvUse() {
        fun makeUseAdapter() {
            usesAdapter = UsesAdapter()
            usesAdapter.updateData(predictModel?.uses ?: listOf())
        }

        makeUseAdapter()
        binding?.rvUses?.adapter = usesAdapter
    }

    private fun setPlantImage() {
        binding?.imvPlant?.setImageResource(predictModel?.image ?: return)
    }

    private fun updateStrings() {
        binding?.tvDescription?.text = predictModel?.des
        binding?.tvName?.text = predictModel?.label
        binding?.imvPlant?.setImageResource(R.drawable.mint)
    }

    private fun setListener() {
        usesAdapter.setListener(object : UsesAdapterListener {
            override fun onClick(item: RemedyModel, position: Int) {
                val remedyFragment = RemedyFragment.newInstance()
                remedyFragment.updateData(item)
                childFragmentManager.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.slide_out
                    )
                    add(R.id.remedy_fragment, remedyFragment)
                    addToBackStack(null)
                }
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
        fun newInstance() = PlantDetailFragment()
    }
}
