package uz.azim.stocks.util.loading;

import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Loading {

    suspend fun hideLoading(progressBar: ProgressBar, button: Button) {
        withContext(Dispatchers.Main) {
            progressBar.isVisible = false
            button.isVisible = false
        }
    }

    fun hideLoading(progressBar: ProgressBar, lineChart: LineChart) {
        progressBar.isVisible = false
        lineChart.isVisible = true
    }

    fun hideLoading(progressBar: ProgressBar, recyclerView: RecyclerView) {
        progressBar.isVisible = false
        recyclerView.isVisible = true
    }

    fun showError(
        progressBar: ProgressBar,
        recyclerView: RecyclerView,
        button: Button,
    ) {
        progressBar.isVisible = false
        recyclerView.isVisible = false
        button.isVisible = true
    }

    fun showLoading(progressBar: ProgressBar, recyclerView: RecyclerView) {
        progressBar.isVisible = true
        recyclerView.isVisible = false
    }

    suspend fun showLoading(progressBar: ProgressBar, button: Button) {
        withContext(Dispatchers.Main) {
            progressBar.isVisible = true
            button.isVisible = false
        }
    }

    fun showLoading(progressBar: ProgressBar, lineChart: LineChart) {
        progressBar.isVisible = true
        lineChart.isVisible = false
    }

    fun notShowLoading(progressBar: ProgressBar, button: Button) {
        button.isVisible = true
        progressBar.isVisible = false
    }
}



