package com.example.android_tflite_classifymedicalplants.Model

import com.google.gson.annotations.SerializedName

class ServerResponse {
    @SerializedName("id")
    private val id: Int = 0

    @SerializedName("name")
    val name: String = ""

    @SerializedName("vnName")
    private val vnName: String = ""

    @SerializedName("scientificName")
    private val scientificName: String = ""

    @SerializedName("family")
    private val family: String = ""

    @SerializedName("uses")
    private val uses: String = ""

    private val images: List<String> = listOf()

}
