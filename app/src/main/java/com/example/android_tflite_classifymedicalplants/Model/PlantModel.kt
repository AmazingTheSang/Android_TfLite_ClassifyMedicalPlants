package com.example.android_tflite_classifymedicalplants.Model

class PlantModel(
    val name: String,
    val des: String,
    val uses: List<String> = listOf(),
    val listImgUrl: List<String> = arrayListOf(),
    val listUsedWith: List<PlantModel> = listOf()
) {

}
