package uz.azim.stocks.util.viewModelFactrory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.companyUseCases.GetCompanyStatsUseCase
import uz.azim.stocks.ui.fragment.chart.vm.ChartVM

class ChartViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChartVM::class.java)) {
            val companyRepo = RepositoryModule.bindCompanyRepo()
            return ChartVM(GetCompanyStatsUseCase(companyRepo)) as T

        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}