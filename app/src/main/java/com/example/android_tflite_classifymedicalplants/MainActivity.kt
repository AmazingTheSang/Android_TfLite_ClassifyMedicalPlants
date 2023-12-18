package com.example.android_tflite_classifymedicalplants

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.ThumbnailUtils
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.android_tflite_classifymedicalplants.Fragment.PlantDetailFragment
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

    private var IMAGE_SIZE = 200

    private lateinit var binding: ActivityMainBinding

    private val plantDetailFragment = PlantDetailFragment.newInstance()

    private var predictModel: PredictModel? = null

    private var resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                var image: Bitmap?
                val contentResolver = contentResolver
                val imageUri = it.data?.data ?: return@registerForActivityResult
                try {
                    val source = ImageDecoder.createSource(contentResolver, imageUri)
                    image = ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.RGBA_F16, true)
                    val dimension = image.width.coerceAtMost(image.height)
                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                    binding.imageView.setImageBitmap(image)
                    showClearButton(image != null)
                    image = Bitmap.createScaledBitmap(image, IMAGE_SIZE, IMAGE_SIZE, true)
                    classifyImage(image)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate()
        setOnClickListeners()
    }

    private fun showClearButton(isShow: Boolean) {
        fun showClearBtn() {
            binding.clearBtn.visibility = View.VISIBLE
            binding.imageView.visibility = View.VISIBLE
            binding.button.visibility = View.GONE
        }

        fun showAddBtn() {
            binding.clearBtn.visibility = View.GONE
            binding.imageView.visibility = View.GONE
            binding.button.visibility = View.VISIBLE
        }

        if (isShow) {
            showClearBtn()
        } else {
            showAddBtn()
        }
    }

    private fun inflate() {
        initBinding()
        setContentView(binding.root)
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    private fun setOnClickListeners() {
        fun openGallery() {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openImageGallery()
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }

        fun clearImage() {
            binding.imageView.setImageResource(android.R.color.transparent)
            binding.tvPredictName.text = null
            showClearButton(false)
        }

        binding.button.setOnClickListener {
            openGallery()
        }
        binding.clearBtn.setOnClickListener {
            clearImage()
        }

        binding.tvPredictName.setOnClickListener {
            showBottomSheetDialog(this.predictModel ?: return@setOnClickListener)
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

    private fun toggleLoad(isLoad: Boolean) {
        binding.loadView.visibility = if (isLoad) {
            View.VISIBLE
        } else {
            View.GONE
        }
        binding.tvPredictName.text = if (isLoad) {
            "waiting for response..."
        } else {
            null
        }
    }

    private fun classifyImage(image: Bitmap) {
        val filesDir: File = applicationContext.filesDir
        val imageFile = File(filesDir, "name" + ".jpg")
        val requestFile = RequestBody.create(MultipartBody.FORM, imageFile)
        toggleLoad(true)
        try {
            val os: OutputStream = FileOutputStream(imageFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, os);
            val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
            val call: Call<List<ServerResponse>?>? =
                RetrofitClient.instance?.myApi?.getImageClass(body)
            call?.enqueue(object : Callback<List<ServerResponse>?> {
                override fun onResponse(
                    call: Call<List<ServerResponse>?>,
                    response: Response<List<ServerResponse>?>
                ) {
                    response.body()?.firstOrNull()?.let { res ->
                            toggleLoad(false)
                            val plantModel =
                                samplePlants.find { it.name.lowercase() == res.name.lowercase() }
                                    ?: return
                            val predictModel = PredictModel(
                                label = plantModel.name,
                                des = plantModel.des,
                                uses = plantModel.uses,
                            )
                            this@MainActivity.predictModel = predictModel
                            binding.tvPredictName.text = predictModel.label
                    }
                }

                override fun onFailure(call: Call<List<ServerResponse>?>, t: Throwable) {
                    print("sang")
                }
            })
            os.flush();
            os.close();
        } catch (e: Exception) {
            Log.e("Sang", "Error writing bitmap", e);
        }
    }

    private fun showBottomSheetDialog(item: PredictModel) {
        plantDetailFragment.updateData(item)
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out
            )
            add(R.id.plant_detail_fragment, plantDetailFragment,)
            addToBackStack(null)
        }
    }
}
