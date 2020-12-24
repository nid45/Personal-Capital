package com.example.personalcapitaltechnicalchallenge.helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.io.BufferedInputStream
import java.io.IOException
import java.net.URL

class MediaContentLoader constructor(
    private val imageView: ImageView,
    private val url: String
) :
    AsyncTask<Void?, Void?, Bitmap?>() {


    override fun onPostExecute(bitmap: Bitmap?) {
        if (bitmap != null) {
            imageView.maxHeight
            imageView.setImageBitmap(bitmap)
        }
    }


    private fun getImageBitmap(url: String): Bitmap? {
        var bitMap: Bitmap? = null
        try {
            val url = URL(url)
            val connection = url.openConnection()
            connection.connect()
            val inputStream = connection.getInputStream()
            val bufferedInputStream = BufferedInputStream(inputStream)
            bitMap = BitmapFactory.decodeStream(bufferedInputStream)
            bufferedInputStream.close()
            inputStream.close()
        } catch (e: IOException) { }
        return bitMap
    }

    override fun doInBackground(vararg voids: Void?): Bitmap? {
        return getImageBitmap(url)
    }

}
