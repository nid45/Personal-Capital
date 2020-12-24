package com.example.personalcapitaltechnicalchallenge.helpers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.io.BufferedInputStream
import java.io.IOException
import java.net.URL


//asynchronous image loader given the article image url provided from the rss feed
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

    /**
     * formats the publication date
     * @param url - the url provided to the picture for each article
     * @return image bitmap associated with article's image
     */
    private fun getImageBitmap(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val url = URL(url)
            val connection = url.openConnection()
            connection.connect()
            val inputStream = connection.getInputStream()
            val bufferedInputStream = BufferedInputStream(inputStream)
            bitmap = BitmapFactory.decodeStream(bufferedInputStream)
            bufferedInputStream.close()
            inputStream.close()
        } catch (e: IOException) { }
        return bitmap
    }

    override fun doInBackground(vararg voids: Void?): Bitmap? {
        return getImageBitmap(url)
    }

}
