package uz.azim.stocks.domain.stocksUseCases

import kotlinx.coroutines.flow.Flow
import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.model.Quote

class GetAllFavsUseCase(private val stocksRepository: StocksRepository) {

    fun execute(): Flow<List<Quote>> = stocksRepository.getAllFavs()
}