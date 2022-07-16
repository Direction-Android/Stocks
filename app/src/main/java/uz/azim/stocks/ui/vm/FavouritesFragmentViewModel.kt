package uz.azim.stocks.ui.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.collect
import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.model.Quote
import kotlinx.coroutines.flow.flow

class FavouritesFragmentViewModel : ViewModel() {
    private val stocksRepository: StocksRepository = RepositoryModule.bindStockRepo()

    fun getFavouritesFlow() = flow{
        stocksRepository.getAllFavs().collect {
            emit(it)
        }
    }

    suspend fun deleteStock(quote: Quote){
        stocksRepository.deleteStock(quote)
    }
}