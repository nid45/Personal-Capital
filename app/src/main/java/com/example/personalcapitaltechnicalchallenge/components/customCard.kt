package com.example.personalcapitaltechnicalchallenge.components

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import java.net.URL


class customCard(context: Context?, attrs: AttributeSet?, title: String, description: String, picture: String) :
    CardView(context!!, attrs) {
    private val horizontalContentLayout: ConstraintLayout
    private val titleTextView: TextView
    private val imageView: ImageView

    init {
        horizontalContentLayout = context?.let { ConstraintLayout(it) }!!


        var maxWidth = ConstraintSet()
        maxWidth.constrainPercentWidth(horizontalContentLayout.id , .45F)
        maxWidth.constrainPercentHeight(horizontalContentLayout.id , .3F)

        horizontalContentLayout.setConstraintSet( maxWidth )
        this.addView(horizontalContentLayout)



        titleTextView = TextView(context)
        titleTextView.setTextColor(Color.parseColor("#000000"))
        titleTextView.text = title
        horizontalContentLayout.addView(titleTextView)

        imageView = ImageView(context)
        val url = URL(picture)
        val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        imageView.setImageBitmap(bmp)
    }
}