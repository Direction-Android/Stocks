package uz.azim.stocks.ui.fragment.chart

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import uz.azim.stocks.R
import uz.azim.stocks.databinding.FragmentChartBinding
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.model.StockStat
import uz.azim.stocks.ui.fragment.BaseFragment
import uz.azim.stocks.ui.fragment.quote.SYMBOL
import uz.azim.stocks.ui.vm.ChartFragmentViewModel
import uz.azim.stocks.util.Resource
import uz.azim.stocks.util.getColor
import uz.azim.stocks.util.getDrawable
import uz.azim.stocks.util.log
import uz.azim.stocks.util.vmfactories.ChartFragmentViewModelFactory

class ChartFragment : BaseFragment<FragmentChartBinding>(R.layout.fragment_chart) {
    private val chartFragmentViewModel by viewModels<ChartFragmentViewModel> { ChartFragmentViewModelFactory() }

    private var formatter: ValueFormatter? = null
    private var symbol: String = ""

    override fun initViewBinding(view: View) = FragmentChartBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        symbol = arguments?.getString(SYMBOL) ?: ""
        if (savedInstanceState == null)
            getStats(symbol)
        setUpChart()
    }

    private fun getStats(symbol: String) {
        showLoading()
        lifecycleScope.launchWhenStarted {
            chartFragmentViewModel.getCompanyStatsFlow(symbol)
                .catch {
                    hideLoading()
                }
                .collect {
                    hideLoading()
                    formatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            return it.data!![value.toInt()].period
                        }
                    }
                    binding.statsChart.xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        valueFormatter = formatter
                    }
                    if (it is Resource.Success)
                        generateEntries(it.data!!)
                }
        }
    }

    private fun generateEntries(data: List<StockStat>) {
        val entriesList = ArrayList<Entry>()
        var i = 0f
        (data as ArrayList<StockStat>).reverse()
        data.forEach { stat ->
            entriesList.add(Entry(i, stat.actual.toFloat()))
            i++
        }

        createChart(entriesList)
    }

    private fun createChart(entriesList: java.util.ArrayList<Entry>) {
        val set = LineDataSet(entriesList, symbol).also { lineSet ->
            lineSet.setDrawFilled(true)
            lineSet.fillDrawable =
                getDrawable(requireContext(), R.drawable.fade_chart)
            lineSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            lineSet.color = getColor(requireContext(), R.color.black)
            lineSet.setCircleColor(getColor(requireContext(), R.color.black))
            lineSet.lineWidth = 2f
        }
        binding.statsChart.apply {
            data = LineData(set)
            invalidate()
        }
    }

    private fun setUpChart() {
        val desc = Description()
        desc.text = "This chart represents only 4 quarters"
        binding.statsChart.apply {
            setTouchEnabled(true)
            setScaleEnabled(true)
            setDrawGridBackground(false)
            setNoDataTextColor(getColor(requireContext(), R.color.black))
            setBorderColor(getColor(requireContext(), R.color.black))
            setBorderWidth(4f)
            description = desc
            isDragEnabled = true
            isScaleXEnabled = true
            isScaleYEnabled = true
        }
    }

    private fun showLoading() {
        binding.apply {
            progress.isVisible = true
            statsChart.isVisible = false
        }
    }

    private fun hideLoading() {
        binding.apply {
            progress.isVisible = false
            statsChart.isVisible = true
        }
    }
}
