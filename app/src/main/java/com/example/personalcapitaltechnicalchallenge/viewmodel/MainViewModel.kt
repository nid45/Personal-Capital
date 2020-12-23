package com.example.personalcapitaltechnicalchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.personalcapitaltechnicalchallenge.models.Article

class MainViewModel() : ViewModel() {
    var article = MutableLiveData<Article>()

    fun init(article:Article){
        this.article.value = article
    }


}