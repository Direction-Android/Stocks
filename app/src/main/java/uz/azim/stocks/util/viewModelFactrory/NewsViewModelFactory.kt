package uz.azim.stocks.util.viewModelFactrory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.companyUseCases.GetCompanyNewsUseCase
import uz.azim.stocks.domain.companyUseCases.GetCompanyStatsUseCase
import uz.azim.stocks.ui.fragment.chart.vm.ChartVM
import uz.azim.stocks.ui.fragment.news.vm.NewsVM

class NewsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsVM::class.java)) {
            val companyRepo = RepositoryModule.bindCompanyRepo()
            return NewsVM(GetCompanyNewsUseCase(companyRepo)) as T

        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}