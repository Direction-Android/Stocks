package uz.azim.stocks.ui.fragment.search.vm

import androidx.lifecycle.ViewModel
import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.stocksUseCases.SearchStockUseCase

class SearchVM(private val searchResultUseCase: SearchStockUseCase) : ViewModel() {


    fun getSearchResult(query: String) {
        searchResultUseCase.execute(query)
    }
}