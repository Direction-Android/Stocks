package uz.azim.stocks.data.repo.stock

import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.azim.stocks.data.local.db.StockDao
import uz.azim.stocks.data.remote.StocksService
import uz.azim.stocks.model.Quote
import uz.azim.stocks.util.log
import java.io.IOException

private const val START = 0

class StockPagingSource(
    private val stocksService: StocksService,
    private val stockDao: StockDao,
    private val listType: String,
    private val favorites: List<Quote>
) : PagingSource<Int, Quote>() {
    override fun getRefreshKey(state: PagingState<Int, Quote>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Quote> {
        val page = params.key
            ?: START
        return try {
            val response = stocksService.getStocks(page, listType)
            val data = response.data?.data
            log("${response.data}")
            if (data?.quotes != null) {
                val quotes = data.quotes
                quotes.map { quote ->
                    quote.isFavorite = favorites.any { fav ->
                        if (fav.symbol == quote.symbol) {
                            quote.isFavorite = true
                            stockDao.updateStock(quote)
                        }
                        fav.symbol == quote.symbol
                    }
                }
                LoadResult.Page(
                    data = quotes,
                    prevKey = if (page == START) null else page - params.loadSize,
                    nextKey = if (quotes.isEmpty() || page >= response.data.data.total) null else page + quotes.size
                )
            } else
                LoadResult.Error(Throwable(response.message))
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }
}
