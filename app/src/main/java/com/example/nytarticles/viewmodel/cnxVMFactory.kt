package com.example.nytarticles.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class cnxVMFactory(private val context:Context) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(cnxVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return cnxVM(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}