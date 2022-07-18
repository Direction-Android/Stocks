package uz.azim.stocks.ui.fragment.favourites.vm

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.stocksUseCases.DeleteStockUseCase
import uz.azim.stocks.domain.stocksUseCases.GetAllFavsUseCase
import uz.azim.stocks.model.Quote
import uz.azim.stocks.ui.BaseViewModel

class FavoriteVM(
    private val getAllFavsUseCase: GetAllFavsUseCase,
    private val deleteStockUseCase: DeleteStockUseCase,

    ) : BaseViewModel() {

    private val stocksRepository: StocksRepository = RepositoryModule.bindStockRepo()

    fun getAllFaves() = viewModelScope.launch(exceptionHandler){ getAllFavsUseCase.execute()}

    fun deleteStock(quote: Quote) =
        viewModelScope.launch(exceptionHandler) { deleteStockUseCase.execute(quote) }
}