package com.example.android_tflite_classifymedicalplants.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.android_tflite_classifymedicalplants.Model.PlantModel
import com.example.android_tflite_classifymedicalplants.R

class PlantAdapter(context: Context, plants: MutableList<String?>) :
    ArrayAdapter<String?>(context, 0, plants ) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var listitemView = convertView
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(context).inflate(R.layout.item_plant, parent, false)
        }

        val imgUrl: String? = getItem(position)
        val courseIV = listitemView?.findViewById<ImageView>(R.id.plantImgv)

        courseIV?.setImageResource(R.drawable.ic_launcher_foreground)
        return listitemView!!
    }
}
