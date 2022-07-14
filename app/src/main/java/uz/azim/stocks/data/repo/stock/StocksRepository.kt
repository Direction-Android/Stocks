package uz.azim.stocks.data.repo.stock

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.azim.stocks.model.Quote
import uz.azim.stocks.model.SearchResult
import uz.azim.stocks.util.Resource

interface StocksRepository {

    suspend fun getStocks(listType: String = "most_actives"): Flow<PagingData<Quote>>

    suspend fun saveStock(quote: Quote)

    suspend fun deleteStock(quote: Quote)

    fun getAllFavs(): Flow<List<Quote>>

    fun searchStock(query:String):Flow<Resource<List<SearchResult>>>
}