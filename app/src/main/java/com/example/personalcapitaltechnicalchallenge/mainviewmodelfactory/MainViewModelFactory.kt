package com.example.personalcapitaltechnicalchallenge.mainviewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.personalcapitaltechnicalchallenge.models.Data
import com.example.personalcapitaltechnicalchallenge.viewmodel.MainViewModel

//viewmodel factory to create our viewmodel to house data needed in the app
class MainViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}