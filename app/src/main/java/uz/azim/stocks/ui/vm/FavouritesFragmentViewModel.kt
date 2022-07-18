package uz.azim.stocks.ui.vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.collect
import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.model.Quote
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.domain.DeleteFromFavouritesUseCase
import uz.azim.stocks.domain.GetFavouritesUseCase

class FavouritesFragmentViewModel(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val deleteFromFavouritesUseCase: DeleteFromFavouritesUseCase
    ) : ViewModel() {

    fun getFavouritesFlow() = flow{
        getFavouritesUseCase.execute().collect {
            emit(it)
        }
    }

    suspend fun deleteStock(quote: Quote){
        deleteFromFavouritesUseCase.execute(quote)
    }
}