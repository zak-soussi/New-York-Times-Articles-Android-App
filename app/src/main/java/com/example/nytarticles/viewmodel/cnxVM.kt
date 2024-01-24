package com.example.nytarticles.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class cnxVM(private val context: Context) : ViewModel() {

    private val _cnxIssue = SingleLiveEvent<Boolean>()
    val cnxIssue: LiveData<Boolean> get() = _cnxIssue

    init {
        checkNetwork()
    }
    private fun isNetworkActive(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    fun checkNetwork() {
        if (!isNetworkActive()) {
            _cnxIssue.postValue(true)
        }
        else{
            _cnxIssue.postValue(false)
        }
    }
}