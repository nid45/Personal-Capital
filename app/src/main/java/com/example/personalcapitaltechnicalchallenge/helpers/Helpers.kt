package com.example.personalcapitaltechnicalchallenge.helpers
import android.os.Build
import android.text.Html
import android.text.Spanned
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


//helper functions for the application
object Helpers {
    /**
     * format the html from what is pulled
     * @param html as pulled
     * @return returns html string
     */
    fun fromHtml(html: String?): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html, 0)
        }
    }

    /**
     * formats the publication date
     * @param dateString - the date in the format it was pulled
     * @return returns the date formatted like mon day, year
     */
    fun formatDateTime(dateString: String?): String? {
        var returnString = dateString
        var simpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
        val date: Date?
        try {
            date = simpleDateFormat.parse(dateString)
            simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
            returnString = simpleDateFormat.format(date) + " "
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return returnString
    }
}
