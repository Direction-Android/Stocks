package uz.azim.stocks.domain.stocksUseCases

import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.model.Quote

class DeleteStockUseCase(private val stocksRepository: StocksRepository) {

    suspend fun execute(quote: Quote) = stocksRepository.deleteStock(quote)
}