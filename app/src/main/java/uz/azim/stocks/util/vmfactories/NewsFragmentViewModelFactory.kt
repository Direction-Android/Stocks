package uz.azim.stocks.util.vmfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.GetCompanyNewsUseCase
import uz.azim.stocks.ui.vm.NewsFragmentViewModel

class NewsFragmentViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val companyRepo = RepositoryModule.bindCompanyRepo()
        return NewsFragmentViewModel(GetCompanyNewsUseCase(companyRepo)) as T
    }
}