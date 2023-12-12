package com.example.android_tflite_classifymedicalplants.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
class PredictModel(
    val label: String = "",
    val des: String = "plant_des",
    val uses: List<String> = listOf(),
    val listImgUrl: List<String> = arrayListOf(),
    val listUsedWith: List<PlantModel> = listOf(),
    var confidence: Float = 0f,
) : Serializable, Parcelable
