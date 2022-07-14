package uz.azim.stocks.util.network

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import uz.azim.stocks.App
import uz.azim.stocks.util.Event
import uz.azim.stocks.util.Variables
import uz.azim.stocks.util.log

/**
 * Network listener for API >= 29
 */
class NetworkListener @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
constructor(private val app: App) {

    fun startNetworkCallback() {
        val cm: ConnectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()

        cm.registerNetworkCallback(
            builder.build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    log("available")
                    Variables.isNetworkConnected.postValue(Event(true))
                }

                override fun onLost(network: Network) {
                    log("unavailable")
                    Variables.isNetworkConnected.postValue(Event(false))
                }
            })
    }
    fun stopNetworkCallback() {
        val cm: ConnectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())
    }

}