package uz.azim.stocks.ui.fragment.stocks.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.WebSocket
import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.di.RepositoryModule

import uz.azim.stocks.model.Quote
import uz.azim.stocks.model.StockUpdate
import uz.azim.stocks.ui.BaseViewModel
import uz.azim.stocks.util.Resource
import uz.azim.stocks.util.log

class StocksVM(
) : BaseViewModel() {
    private val stocksRepository: StocksRepository = RepositoryModule.bindStockRepo()

    private val _quotes = MutableStateFlow<Resource<PagingData<Quote>>>(Resource.InitialState())
    val quotes = _quotes.asStateFlow()

    init {
        getStocks()
    }

    private fun getStocks() = viewModelScope.launch(exceptionHandler) {
        stocksRepository.getStocks()
            .cachedIn(viewModelScope)
            .collectLatest {
                _quotes.value = Resource.Success(it)
            }
    }

    fun saveStock(quote: Quote) = viewModelScope.launch(exceptionHandler) {
        stocksRepository.saveStock(quote)
    }

    fun deleteStock(quote: Quote) = viewModelScope.launch(exceptionHandler) {
        stocksRepository.deleteStock(quote)
    }

}
