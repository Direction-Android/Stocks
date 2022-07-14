package uz.azim.stocks.data.remote

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query
import uz.azim.stocks.model.News
import uz.azim.stocks.model.StockStat
import uz.azim.stocks.util.Resource

interface CompanyService {

    @GET("company-news")
    fun companyNews(
        @Query("symbol") symbol: String,
        @Query("from") fromDate: String,
        @Query("to") toDate: String
    ): Flow<Resource<List<News>>>

    @GET("stock/earnings")
    fun stockStat(@Query("symbol") symbol: String): Flow<Resource<List<StockStat>>>
}