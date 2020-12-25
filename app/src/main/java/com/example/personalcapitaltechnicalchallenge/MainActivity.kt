package com.example.personalcapitaltechnicalchallenge

import android.R
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.example.personalcapitaltechnicalchallenge.helpers.MediaContentLoader
import com.example.personalcapitaltechnicalchallenge.ui.MainFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val coordinatorLayout = CoordinatorLayout(this)
        coordinatorLayout.id = View.generateViewId()

        val coordinatorLayoutParams =
            CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.MATCH_PARENT,
                CoordinatorLayout.LayoutParams.MATCH_PARENT
            )


        val linearLayout = LinearLayout(this)
        linearLayout.gravity = Gravity.CENTER_HORIZONTAL
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearLayout.setBackgroundColor(Color.BLACK)

        //create toolbar at top of screen
        val toolbar = Toolbar(this)
        toolbar.id = View.generateViewId()
        var layout = Toolbar.LayoutParams(
            Toolbar.LayoutParams.MATCH_PARENT,
            Toolbar.LayoutParams.WRAP_CONTENT
        )
        toolbar.textAlignment = View.TEXT_ALIGNMENT_CENTER
        layout.gravity = Gravity.CENTER
        toolbar.layoutParams = layout

        //title
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        toolbar.title = "Daily Capital Blog"
        val bt = Button(this)
        bt.text = "⟲"
        bt.textSize = 30F
        bt.setBackgroundColor(Color.BLACK)
        val params: Toolbar.LayoutParams =
            Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.RIGHT
        bt.layoutParams = params
        setSupportActionBar(toolbar)
        toolbar.addView(bt)
        linearLayout.addView(toolbar)

        refreshButton = bt


        val frameLayout = FrameLayout(this)
        frameLayout.id = View.generateViewId()
        frameLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        frameLayout.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        linearLayout.addView(frameLayout)

        coordinatorLayout.addView(linearLayout)
        setContentView(coordinatorLayout, coordinatorLayoutParams)



        //lock in portrait mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if (savedInstanceState == null) {
            mainLayout = frameLayout
            val frag =
                MainFragment()

            //go to Main fragment to display articles

            supportFragmentManager.beginTransaction().add(frag, "main").commit()

            supportFragmentManager.beginTransaction()
                .replace(frameLayout.id, frag).commit()
        }
    }

    companion object{
        lateinit var mainLayout: FrameLayout
        lateinit var refreshButton: Button
    }


}




