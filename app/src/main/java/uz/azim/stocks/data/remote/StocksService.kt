package uz.azim.stocks.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import uz.azim.stocks.model.BaseResponse
import uz.azim.stocks.util.Resource

interface StocksService {

    @GET("co/collections")
    suspend fun getStocks(
        @Query("start") start: Int,
        @Query("list") listType: String
    ): Resource<BaseResponse>

}
