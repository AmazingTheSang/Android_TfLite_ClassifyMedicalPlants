package com.example.android_tflite_classifymedicalplants.Fragment

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.android_tflite_classifymedicalplants.databinding.ActivityMainBinding
import com.example.android_tflite_classifymedicalplants.databinding.PredictBottomSheetBinding
import com.example.android_tflite_classifymedicalplants.databinding.RemedyFragmentBinding

class RemedyFragment:  AppCompatActivity() {
    private lateinit var binding: RemedyFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = RemedyFragmentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
