package com.example.android_tflite_classifymedicalplants.Model

import android.content.Context
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
class PredictModel(
    val label: String,
    val description: String = "haha",
    var confidence: Float = 0f,
): Serializable, Parcelable
