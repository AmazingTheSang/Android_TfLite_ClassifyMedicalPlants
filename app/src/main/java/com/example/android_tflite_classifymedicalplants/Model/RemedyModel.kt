package com.example.android_tflite_classifymedicalplants.Model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RemedyModel(
    val name: String,
    val ingredients: List<String> = listOf(),
    val plantUsed: List<RemedyFragmentPlantModel> = listOf()
) : Parcelable {
}
@Parcelize
class RemedyFragmentPlantModel(
    val name: String,
    val image: Bitmap?,
): Parcelable
