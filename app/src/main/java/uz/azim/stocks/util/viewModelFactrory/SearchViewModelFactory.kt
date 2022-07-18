package uz.azim.stocks.util.viewModelFactrory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.stocksUseCases.SearchStockUseCase
import uz.azim.stocks.ui.fragment.search.vm.SearchVM

class SearchViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchVM::class.java)) {
            val stockRepo = RepositoryModule.bindStockRepo()
            return SearchVM(SearchStockUseCase(stockRepo)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}