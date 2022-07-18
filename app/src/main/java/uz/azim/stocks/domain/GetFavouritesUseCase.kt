package uz.azim.stocks.domain

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.data.repo.stock.StocksRepository

class GetFavouritesUseCase(private val stocksRepository: StocksRepository) {
    fun execute() = flow {
        stocksRepository.getAllFavs().collect {
            emit(it)
        }
    }
}