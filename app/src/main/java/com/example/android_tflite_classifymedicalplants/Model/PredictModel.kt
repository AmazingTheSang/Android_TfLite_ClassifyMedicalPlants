package com.example.android_tflite_classifymedicalplants.Model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
class PredictModel(
    val label: String = "",
    val des: String = "plant_des",
    val uses: List<RemedyModel> = listOf(),
    val avatar: Bitmap? = null,
    val listImgUrl: List<String> = arrayListOf(),
    val listUsedWith: List<PlantModel> = listOf(),
    var confidence: Float = 0f,
) : Serializable, Parcelable
