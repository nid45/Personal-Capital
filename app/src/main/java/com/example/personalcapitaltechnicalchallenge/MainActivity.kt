package com.example.personalcapitaltechnicalchallenge

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.personalcapitaltechnicalchallenge.mainviewmodelfactory.MainViewModelFactory
import com.example.personalcapitaltechnicalchallenge.models.Article
import com.example.personalcapitaltechnicalchallenge.models.Item
import com.example.personalcapitaltechnicalchallenge.networking.ApiCall
import com.example.personalcapitaltechnicalchallenge.viewmodel.MainViewModel
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    mainViewModelFactory = MainViewModelFactory()

    viewModel = ViewModelProvider(
    this,
    mainViewModelFactory
    ).get(MainViewModel::class.java)

    var art = ApiCall().execute()
    viewModel.init(parseData(art.get()))

    }

    private fun parseData(message: String): Article {
        var jsonObject = JSONObject(message)

        var description = jsonObject.getString("description").toString()
        var feed_url = jsonObject.getString("feed_url").toString()
        var home_page_url = jsonObject.getString("home_page_url").toString()
        var title = jsonObject.getString("title").toString()
        var user_comment = jsonObject.getString("user_comment").toString()
        var version = jsonObject.getString("version").toString()
        var itemArray = JSONArray(jsonObject.getString("items"))

        var itemList:MutableList<Item> = mutableListOf<Item>()
        for (i in 0 .. itemArray.length() - 1) {
            var item = itemArray[i] as JSONObject
            var author = item.get("author").toString()
            var category = item.getString("category").toString()
            var content = item.getString("content").toString()
            var content_html = item.getString("content_html").toString()
            var date_modified= item.getString("date_modified").toString()
            var date_published = item.getString("date_published").toString()
            var encoded_title = item.getString("encoded_title").toString()
            var featured_image = item.getString("featured_image").toString()
            var id = item.getString("id").toString()
            var insight_summary = item.getString("insight_summary").toString()
            var summary = item.getString("summary").toString()
            var summary_html = item.getString("summary_html").toString()
            var title = item.getString("title").toString()
            var url = item.getString("url").toString()
            Log.i("fuck", author)
            itemList.add((Item(author, category, content, content_html, date_modified, date_published, encoded_title, featured_image, id, insight_summary,
                summary, summary_html, title, url)))
        }
        return Article(description, feed_url, home_page_url, itemList, title, user_comment, version)

    }
}