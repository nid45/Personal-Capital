package com.example.personalcapitaltechnicalchallenge.helpers
import android.R
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.Spanned
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Helpers {
    /**
     * format the html text from the xml input
     *
     * @param html html formatted text
     * @return returns an the string with the text properly formatted for the text view
     */
    fun fromHtml(html: String?): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html, 0)
        }
    }



    fun formatDateTime(dateString: String?): String? {
        var dateString = dateString
        var simpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
        val date: Date?
        try {
            date = simpleDateFormat.parse(dateString)
            simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
            dateString = simpleDateFormat.format(date) + " "
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateString
    }
}
