package uz.azim.stocks.domain.stocksUseCases

import kotlinx.coroutines.flow.Flow
import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.model.SearchResult
import uz.azim.stocks.util.Resource

class SearchStockUseCase(private val stocksRepository: StocksRepository) {

    fun execute(query: String): Flow<Resource<List<SearchResult>>> =
        stocksRepository.searchStock(query)
}
