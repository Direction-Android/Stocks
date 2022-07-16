package uz.azim.stocks.ui.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.model.StockStat
import uz.azim.stocks.util.Resource

class ChartFragmentViewModel: ViewModel() {
    private val companyRepo = RepositoryModule.bindCompanyRepo()

    fun getCompanyStatsFlow(symbol: String) = flow<Resource<List<StockStat>>>{
        companyRepo.getCompanyStats(symbol).collect {
            emit(it)
        }
    }
}