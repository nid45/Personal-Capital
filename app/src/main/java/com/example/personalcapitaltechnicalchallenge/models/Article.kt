package com.example.personalcapitaltechnicalchallenge.models

import java.io.Serializable

data class Data(
    val description: String,
    val feed_url: String,
    val home_page_url: String,
    val items: List<Article>,
    val title: String,
    val user_comment: String,
    val version: String
)

data class Article(
    val author: Any,
    val category: String,
    val content: String,
    val content_html: String,
    val date_modified: String,
    val date_published: String,
    val encoded_title: String,
    val featured_image: String,
    val id: String,
    val insight_summary: String,
    val summary: String,
    val summary_html: String,
    val title: String,
    val url: String
) : Serializable //serializable so we can pass this data between fragments using a bundle