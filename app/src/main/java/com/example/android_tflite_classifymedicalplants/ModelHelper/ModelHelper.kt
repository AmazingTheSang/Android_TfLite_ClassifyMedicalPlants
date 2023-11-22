package com.example.android_tflite_classifymedicalplants.ModelHelper

import android.content.Context
import android.graphics.Bitmap
import com.example.android_tflite_classifymedicalplants.Model.PlantModel
import com.example.android_tflite_classifymedicalplants.Model.PredictModel
import com.example.android_tflite_classifymedicalplants.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ModelHelper(private val context: Context, private val imageSize: Int) {

    fun classifyImage(image: Bitmap): List<PredictModel> {
        fun getByteBuffer(): ByteBuffer {
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            val intValues = IntArray(imageSize * imageSize)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val value = intValues[pixel++]
                    byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((value and 0xFF) * (1f / 255f))
                }
            }
            return byteBuffer
        }

        fun getConfidences(): FloatArray {
            val model = Model.newInstance(context)
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 200, 200, 3), DataType.FLOAT32)
            val byteBuffer = getByteBuffer()
            inputFeature0.loadBuffer(byteBuffer)
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            model.close()
            return outputFeature0.floatArray
        }

        fun getPredictModels(plantModels: List<PlantModel>, confidences: FloatArray): List<PredictModel> {
            val predictModels = plantModels.map { PredictModel(label = it.name, description = it.des, listImgUrl = it.listImgUrl) }
            predictModels.withIndex().onEach { (index, model) ->
                if (confidences.indices.contains(index)) {
                    model.confidence = confidences[index]
                }
            }
            return predictModels
        }

        fun getPlantModels(): List<PlantModel> {
            return listOf(
                PlantModel(
                    "Bạc hà",
                    " Bạc hà là một loại rau gia vị, đồng thời là một vị thuốc rất phổ biến dùng chữa cảm sốt, cảm mạo, nhức đầu, kích thích tiêu hóa, giảm đau",
                    arrayListOf(

                    )
                ),
                PlantModel("Bồ đề", "Cây bồ đề là một loại cây có nguồn gốc từ Ấn Độ, là loại cây thiêng liêng gắn liền với Phật pháp. Đây là loại cây cảnh sân vườn được trồng phổ biến ở Việt Nam."),
                PlantModel("Cà ri", "Cây cà ri còn gọi là curry hay Murraya koenigii là một loại cây có nguồn gốc từ Ấn Độ. Lá của loại cây này không chỉ có hương thơm đặc trưng mà còn có những công dụng thần kỳ đối với sức khỏe của con người."),
                PlantModel("Cải bẹ xanh", ""),
                PlantModel("Chanh", ""),
                PlantModel("Chùm ngây", ""),
                PlantModel("Cỏ cà ri", ""),
                PlantModel("Dâm bụt", ""),
            )
        }

        return getPredictModels(
            getPlantModels(), getConfidences()
        ).sortedBy { it.confidence }.reversed()
    }
}
