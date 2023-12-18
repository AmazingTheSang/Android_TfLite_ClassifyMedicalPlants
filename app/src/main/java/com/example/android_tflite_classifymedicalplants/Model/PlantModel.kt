package com.example.android_tflite_classifymedicalplants.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class PlantModel(
    open val name: String,
    open val des: String,
    open val uses: List<RemedyModel> = listOf(),
    val image: Int? = null
) : Parcelable
