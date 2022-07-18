package uz.azim.stocks.domain

import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.model.Quote

class DeleteFromFavouritesUseCase(private val stocksRepository: StocksRepository) {

    suspend fun execute(quote: Quote){
        stocksRepository.deleteStock(quote)
    }

}