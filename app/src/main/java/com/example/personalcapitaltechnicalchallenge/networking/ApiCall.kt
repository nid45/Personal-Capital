package com.example.personalcapitaltechnicalchallenge.networking

import android.os.AsyncTask
import android.util.Log
import androidx.core.content.contentValuesOf
import com.example.personalcapitaltechnicalchallenge.models.Data
import com.example.personalcapitaltechnicalchallenge.models.Article
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class ApiCall : AsyncTask<String, Integer, String>() {

    override fun doInBackground(vararg params: String?): String? {
            val BaseURL = "https://www.personalcapital.com/blog/feed/json"
            val obj = URL(BaseURL)
            val connection: HttpsURLConnection = obj.openConnection() as HttpsURLConnection
            connection.requestMethod = "GET"
            connection.connect()


            if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
                val json = BufferedReader(InputStreamReader(connection.inputStream))
                var message = String()
                var line: String
                for(line in json.lines()) {
                    if (line == null || line.isEmpty())
                        break
                    message += line
                }

                return message

            } else {
                return null
            }
        }
    }

