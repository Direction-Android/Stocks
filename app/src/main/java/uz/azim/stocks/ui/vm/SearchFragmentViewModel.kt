package uz.azim.stocks.ui.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.GetSearchResultsUseCase

class SearchFragmentViewModel(private val getSearchResultsUseCase: GetSearchResultsUseCase) :
    ViewModel() {
    private val stocksRepository: StocksRepository = RepositoryModule.bindStockRepo()

    fun getSearchResult(query: String) = flow {
        getSearchResultsUseCase.execute(query).collect {
            emit(it)
        }
    }
}