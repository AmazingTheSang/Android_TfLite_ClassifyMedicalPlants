package com.example.android_tflite_classifymedicalplants

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.android_tflite_classifymedicalplants.Adapter.ConfidencesAdapter
import com.example.android_tflite_classifymedicalplants.Adapter.ConfidencesAdapterListener
import com.example.android_tflite_classifymedicalplants.Fragment.PredictModelBottomSheet
import com.example.android_tflite_classifymedicalplants.Model.PredictModel
import com.example.android_tflite_classifymedicalplants.ModelHelper.ModelHelper
import com.example.android_tflite_classifymedicalplants.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


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
        val predictModels = ModelHelper(applicationContext, imageSize).classifyImage(image)
        adapter.updateData(predictModels)
    }

    private fun showBottomSheetDialog(item: PredictModel) {
        val blankFragment = PredictModelBottomSheet.newInstance(item)
        blankFragment.show(supportFragmentManager, blankFragment.tag)
    }
}
