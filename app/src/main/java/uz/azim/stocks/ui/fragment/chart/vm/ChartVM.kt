package uz.azim.stocks.ui.fragment.chart.vm

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.data.repo.company.CompanyRepo
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.companyUseCases.GetCompanyStatsUseCase
import uz.azim.stocks.ui.BaseViewModel

class ChartVM(private val getCompanyStatsUseCase: GetCompanyStatsUseCase) : BaseViewModel() {

    private val companyRepo: CompanyRepo = RepositoryModule.bindCompanyRepo()
    fun getCompanyStats(symbol: String) =
        flow { getCompanyStatsUseCase.execute(symbol).collect { emit(it) } }
}