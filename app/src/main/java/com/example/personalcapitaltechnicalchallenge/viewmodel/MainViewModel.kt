package com.example.personalcapitaltechnicalchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.personalcapitaltechnicalchallenge.models.Article
import com.example.personalcapitaltechnicalchallenge.models.Data
import org.json.JSONArray
import org.json.JSONObject

//viewmodel to hold the data needed
class MainViewModel : ViewModel() {
    var article = MutableLiveData<Data>()

    fun init(article:Data){
        this.article.value = article
    }

    object parser {
        fun parseData(message: String?): Data {
            val jsonObject = JSONObject(message)

            val description = jsonObject.getString("description").toString()
            val feedUrl = jsonObject.getString("feed_url").toString()
            val homePageUrl = jsonObject.getString("home_page_url").toString()
            val titleData = jsonObject.getString("title").toString()
            val userComment = jsonObject.getString("user_comment").toString()
            val version = jsonObject.getString("version").toString()
            val itemArray = JSONArray(jsonObject.getString("items"))

            val itemList: MutableList<Article> = mutableListOf()
            for (i in 0 until itemArray.length()) {
                val item = itemArray[i] as JSONObject
                val authorob = JSONObject(item.get("author").toString())
                val author = authorob.get("name").toString()
                val category = item.getString("category").toString()
                val content = item.getString("content").toString()
                val contentHtml = item.getString("content_html").toString()
                val dateModified = item.getString("date_modified").toString()
                val datePublished = item.getString("date_published").toString()
                val encodedTitle = item.getString("encoded_title").toString()
                val featuredImage = item.getString("featured_image").toString()
                val id = item.getString("id").toString()
                val insightSummary = item.getString("insight_summary").toString()
                val summary = item.getString("summary").toString()
                val summaryHtml = item.getString("summary_html").toString()
                val title = item.getString("title").toString()
                val url = item.getString("url").toString()
                itemList.add(
                    (Article(
                        author,
                        category,
                        content,
                        contentHtml,
                        dateModified,
                        datePublished,
                        encodedTitle,
                        featuredImage,
                        id,
                        insightSummary,
                        summary,
                        summaryHtml,
                        title,
                        url
                    ))
                )
            }
            return Data(
                description,
                feedUrl,
                homePageUrl,
                itemList,
                titleData,
                userComment,
                version
            )

        }
    }
}