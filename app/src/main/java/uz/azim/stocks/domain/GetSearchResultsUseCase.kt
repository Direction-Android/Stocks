package uz.azim.stocks.domain

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.data.repo.stock.StocksRepository

class GetSearchResultsUseCase(private val stocksRepository: StocksRepository) {

    fun execute(query: String) = flow {
        stocksRepository.searchStock(query).collect {
            emit(it)
        }
    }

}