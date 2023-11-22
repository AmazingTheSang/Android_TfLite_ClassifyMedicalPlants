package com.example.android_tflite_classifymedicalplants.ModelHelper

import android.content.Context
import android.graphics.Bitmap
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

        fun getPredictModels(labels: List<String>, confidences: FloatArray): List<PredictModel> {
            val predictModels = labels.map { PredictModel(label = it) }
            predictModels.withIndex().onEach { (index, model) ->
                if (confidences.indices.contains(index)) {
                    model.confidence = confidences[index]
                }
            }
            return predictModels
        }

        fun getLabels(): List<String> {
            return listOf(
                "Bạc hà","Bồ đề","Cà ri","Cải bẹ xanh","Chanh",
                "Chùm ngây","Cỏ cà ri","Dâm bụt","Dền cơm","Hoàng hậu",
                "Hương nhu","Lài trâu","Lựu","Mận lý","Mít",
                "Mồng tơi","Nhài","Ổi","Riềng nếp","Siro",
                "Tần lá dày","Trầu không","Trúc đào","Trứng cá","Vả",
                "Vối rừng","Xoan Ấn Độ","Xoài","Đàn hương","Đậu dầu"
            )
        }

        return getPredictModels(
            getLabels(), getConfidences()
        ).sortedBy { it.confidence }.reversed()
    }
}
