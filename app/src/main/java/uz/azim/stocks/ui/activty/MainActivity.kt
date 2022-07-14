package uz.azim.stocks.ui.activty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import uz.azim.stocks.R
import uz.azim.stocks.databinding.ActivityMainBinding
import uz.azim.stocks.util.Variables
import uz.azim.stocks.util.showSnackbar


class MainActivity : AppCompatActivity() {
    private var isFirstNetworkCheck = true

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Variables.isNetworkConnected.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { isNetworkAvailable ->
                if (isNetworkAvailable) {
                    onNetworkAvailable()
                } else
                    onNetworkLost()
            }
        })
    }

    private fun onNetworkAvailable() {
        if (!isFirstNetworkCheck)
            binding.root.showSnackbar(getString(R.string.network_back))
        isFirstNetworkCheck = false
    }

    private fun onNetworkLost() {
        binding.root.showSnackbar(getString(R.string.no_network))
    }
}
