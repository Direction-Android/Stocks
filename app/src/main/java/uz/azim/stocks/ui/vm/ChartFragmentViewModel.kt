package uz.azim.stocks.ui.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.data.repo.company.CompanyRepo
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.GetCompanyStatsUseCase

class ChartFragmentViewModel(
    private val getCompanyStatsUseCase: GetCompanyStatsUseCase) : ViewModel() {

    fun getCompanyStatsFlow(symbol: String) = flow {
        getCompanyStatsUseCase.execute(symbol).collect {
            emit(it)
        }
    }
}