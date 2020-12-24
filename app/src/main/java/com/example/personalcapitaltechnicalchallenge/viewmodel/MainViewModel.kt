package com.example.personalcapitaltechnicalchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.personalcapitaltechnicalchallenge.models.Data

class MainViewModel : ViewModel() {
    var article = MutableLiveData<Data>()

    fun init(article:Data){
        this.article.value = article
    }


}