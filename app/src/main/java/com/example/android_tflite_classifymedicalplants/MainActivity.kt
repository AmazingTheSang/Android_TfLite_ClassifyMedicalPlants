package com.example.android_tflite_classifymedicalplants

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.android_tflite_classifymedicalplants.Adapter.ConfidencesAdapter
import com.example.android_tflite_classifymedicalplants.Adapter.ConfidencesAdapterListener
import com.example.android_tflite_classifymedicalplants.Fragment.PredictModelBottomSheet
import com.example.android_tflite_classifymedicalplants.Model.PlantModel
import com.example.android_tflite_classifymedicalplants.Model.PredictModel
import com.example.android_tflite_classifymedicalplants.Model.ServerResponse
import com.example.android_tflite_classifymedicalplants.ModelHelper.RetrofitClient
import com.example.android_tflite_classifymedicalplants.databinding.ActivityMainBinding
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


open class MainActivity : AppCompatActivity() {
    private var imageSize = 200

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: ConfidencesAdapter

    private var resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                var image: Bitmap?
                val contentResolver = contentResolver
                val imageUri = it.data?.data ?: return@registerForActivityResult
                try {
                    image = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    } else {
                        val source = ImageDecoder.createSource(contentResolver, imageUri)
                        ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.RGBA_F16, true)
                    } ?: return@registerForActivityResult
                    val dimension = image.width.coerceAtMost(image.height)
                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                    binding.imageView.setImageBitmap(image)
                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, true)
                    classifyImage(image)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    open fun getRealPathFromURI(contentURI: Uri?, context: Activity): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        @Suppress("deprecation") val cursor = context.managedQuery(
            contentURI, projection, null,
            null, null
        ) ?: return null
        val column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        return if (cursor.moveToFirst()) {
            // cursor.close();
            cursor.getString(column_index)
        } else null
        // cursor.close();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate()
        configRecycleView()
        setOnClickListeners()
    }

    private fun inflate() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun configRecycleView() {
        adapter = ConfidencesAdapter()
        adapter.setListener(object : ConfidencesAdapterListener {
            override fun onClick(item: PredictModel, position: Int) {
                showBottomSheetDialog(item)
            }
        })
        binding.rvConfidences.adapter = adapter
    }

    private fun setOnClickListeners() {
        binding.button.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openImageGallery()
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    private fun openImageGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "*/*"
        galleryIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            arrayListOf<Intent>().toTypedArray<Parcelable>()
        )
        resultLauncher.launch(galleryIntent)
    }

    private fun classifyImage(image: Bitmap) {
        val filesDir: File = applicationContext.filesDir
        val imageFile = File(filesDir, "name" + ".jpg")
        val requestFile = RequestBody.create(MultipartBody.FORM, imageFile)
        try {
            val os: OutputStream = FileOutputStream(imageFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, os);
            val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
            val call: Call<List<ServerResponse>?>? = RetrofitClient.instance?.myApi?.getImageClass(body)
            call?.enqueue(object : Callback<List<ServerResponse>?> {
                override fun onResponse(
                    call: Call<List<ServerResponse>?>,
                    response: Response<List<ServerResponse>?>
                ) {
                    response.body()?.firstOrNull()?.let { res ->
                        val plantModel = samplePlants.find { it.name.lowercase() == res.name.lowercase() } ?: return
                        val predictModel = PredictModel(
                            label = plantModel.name,
                            des = plantModel.des,
                            uses = plantModel.uses,
                            listImgUrl = plantModel.listImgUrl
                        )
                        adapter.updateData(listOf(predictModel))
                        showBottomSheetDialog(predictModel)
                    }
                }

                override fun onFailure(call: Call<List<ServerResponse>?>, t: Throwable) {
                    print("sang")
                }
            })
            os.flush();
            os.close();
        } catch ( e: Exception) {
            Log.e("Sang", "Error writing bitmap", e);
        }

//        val predictModels = ModelHelper(applicationContext, imageSize).classifyImage(image)
//        adapter.updateData(predictModels)
    }

    private fun showBottomSheetDialog(item: PredictModel) {
        adapter.updateData(listOf())
        val blankFragment = PredictModelBottomSheet.newInstance(item)
        blankFragment.show(supportFragmentManager, blankFragment.tag)
    }
}
