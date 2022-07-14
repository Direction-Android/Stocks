package uz.azim.stocks.data.repo.stock

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import uz.azim.stocks.data.local.db.StockDao
import uz.azim.stocks.data.remote.SearchService
import uz.azim.stocks.data.remote.StocksService
import uz.azim.stocks.model.Quote
import uz.azim.stocks.model.SearchResult
import uz.azim.stocks.util.Resource

class StocksRepositoryImpl
constructor(
    private val stocksService: StocksService,
    private val searchService: SearchService,
    private val stockDao: StockDao,
) : StocksRepository {
    override fun getAllFavs() = stockDao.getAllFavoriteStocks()

    override suspend fun saveStock(quote: Quote) {
        stockDao.saveStock(quote)
    }

    override suspend fun deleteStock(quote: Quote) {
        stockDao.deleteStock(quote)
    }

    //Didn't add caching due to data update
    override suspend fun getStocks(listType: String): Flow<PagingData<Quote>> {
        val favs = stockDao.getAllFavoriteStocks().first()
        return Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = false)
        ) {
            StockPagingSource(stocksService, stockDao, listType, favs)
        }.flow
    }

    override fun searchStock(query: String) = flow<Resource<List<SearchResult>>> {
        searchService.searchStock(query)
            .collect {
                when (it) {
                    is Resource.InitialState -> {
                        emit(Resource.InitialState())
                    }
                    is Resource.Loading -> {
                        emit(Resource.Loading())
                    }
                    is Resource.Error -> {
                        emit(Resource.Error(it.message.toString()))
                    }
                    is Resource.Success -> {
                        it.data?.let { response ->
                            emit(Resource.Success(response.result))
                        }
                    }
                }
            }
    }
}
