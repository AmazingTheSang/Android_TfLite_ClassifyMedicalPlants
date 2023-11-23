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

        fun getPredictModels(
            plantModels: List<PlantModel>,
            confidences: FloatArray
        ): List<PredictModel> {
            val predictModels = plantModels.map {
                PredictModel(
                    label = it.name,
                    description = it.des,
                    listImgUrl = it.listImgUrl
                )
            }
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
                    "Bạc hà là một loại cây thân thảo, cao khoảng 60-80cm, có tên khoa học là Mentha Arvensis, tên tiếng Anh là mint. Cây bạc hà thân hình vuông, màu xanh hoặc tím nhạt.",
                    arrayListOf(
                        "", "", ""
                    )
                ),
                PlantModel(
                    "Bạch đồng nữ",
                    " Bạch đồng nữ hay còn gọi là cây mò trắng, cây bấn trắng. Tên khoa học là Clerodendrum fragrans Vent, thuộc họ cỏ roi ngựa, có pháp danh khoa học là Verbenaceae.",
                    arrayListOf(
                        "", "", ""
                    )
                ),
                PlantModel(
                    "Cam thảo đất",
                    "Cam thảo đất hay cam thảo nam có tên khoa học là Seoparia Dulcis L, là loại cây thân thảo, mọc thẳng, cao 30 - 80cm, thân tròn, thuộc họ thân thảo, thân mềm, rễ hình trụ to.",
                    arrayListOf(
                        "", "", ""
                    )
                ),

                PlantModel(
                    "Cỏ mần trầu", "Cỏ mần trầu còn được gọi là Thanh tâm thảo, Ngưu cân thảo, Cỏ chì tía, Cỏ vườn trầu, có tên khoa học là Eleusine indica (L.) Gaertn – thuộc họ Lúa (Poaceae).", arrayListOf(
                        "", "", ""
                    )
                ),
                PlantModel(
                    "Cúc tần", "Cúc tần hay cây từ bi, lức, lức ấn là loài thực vật có hoa thuộc họ Cúc. Cây cúc tần chứa hợp chất β-sitosterol và stigmasterol, là những chất có tác dụng chữa bệnh đái tháo đường.", arrayListOf(
                        "", "", ""
                    )
                ),
                PlantModel(
                    "Diệp hạ châu", "Cây chó đẻ răng cưa hay còn gọi là cây chó đẻ, diệp hạ châu, diệp hạ châu đắng, cây cau trời. Tên Hán Việt khác: trân châu thảo, nhật khai dạ bế, diệp hậu châu. Cây có hạt tròn xếp thành hàng dưới lá nên gọi là diệp hạ châu.", arrayListOf(
                        "", "", ""
                    )
                ),
                PlantModel(
                    "Hạ khô thảo", "Hạ khô thảo với tên khoa học đầy đủ là Spira Prunella Vulgaris, thuộc họ Labiatae", arrayListOf(
                        "", "", ""
                    )
                ),
                PlantModel(
                    "Hoắc hương", "Hoắc hương là vị thuốc quý được dùng nhiều trong Đông y", arrayListOf(
                        "", "", ""
                    )
                ),
                PlantModel(
                    "Húng chanh", "Húng chanh hay tần dày lá, rau thơm lùn, rau thơm lông, rau tần là cây thuộc họ Hoa môi", arrayListOf(
                        "", "", ""
                    )
                ),
                PlantModel(
                    "Hy thiêm", "Hy thiêm, hay còn có tên dân gian khác là cỏ đĩ, cứt lợn, hy kiểm thảo, hy tiên, niêm hồ thái, chư cao, hổ cao, chó đẻ, nụ áo rìa..., là một loài thực vật thuộc họ Cúc", arrayListOf(
                        "", "", ""
                    )
                ),
                PlantModel(
                    "Kinh giới", "Kinh giới, kinh giới rìa hay kinh giới trồng là loài cây thảo thuộc họ Hoa môi, là một loại rau thơm và cây thuốc", arrayListOf(
                        "", "", ""
                    )
                ),
                PlantModel(
                    "Sâm ngọc linh", "Sâm Ngọc Linh là một loài cây thuộc Họ Cuồng cuồng, còn gọi là sâm Việt Nam", arrayListOf(
                        "", "", ""
                    )
                ),
            )
        }

        return getPredictModels(
            getPlantModels(), getConfidences()
        ).sortedBy { it.confidence }.reversed()
    }
}
