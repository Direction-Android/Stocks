package uz.azim.stocks.ui.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.di.RepositoryModule

class ChartFragmentViewModel : ViewModel() {
    private val companyRepo = RepositoryModule.bindCompanyRepo()

    fun getCompanyStatsFlow(symbol: String) = flow {
        companyRepo.getCompanyStats(symbol).collect {
            emit(it)
        }
    }
}