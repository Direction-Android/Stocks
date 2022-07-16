package uz.azim.stocks.ui.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.di.RepositoryModule

class SearchFragmentViewModel: ViewModel() {
    private val stocksRepository: StocksRepository = RepositoryModule.bindStockRepo()

    fun getSearchResult(query: String) = flow {
        stocksRepository.searchStock(query).collect {
            emit(it)
        }
    }
}