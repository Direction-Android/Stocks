package uz.azim.stocks.util.vmfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.GetSearchResultsUseCase
import uz.azim.stocks.ui.vm.SearchFragmentViewModel

class SearchFragmentViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val stocksRepository = RepositoryModule.bindStockRepo()
        return SearchFragmentViewModel(GetSearchResultsUseCase(stocksRepository)) as T
    }
}