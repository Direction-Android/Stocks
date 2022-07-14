package uz.azim.stocks.util

import androidx.lifecycle.MutableLiveData

object Variables {
    var isNetworkConnected = MutableLiveData<Event<Boolean>>()
}