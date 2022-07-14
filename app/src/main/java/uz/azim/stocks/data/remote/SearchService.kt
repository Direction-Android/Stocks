package uz.azim.stocks.data.remote

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query
import uz.azim.stocks.model.SearchResponse
import uz.azim.stocks.util.Resource

/**
 * Experimental with Flow return type
 */

interface SearchService {

    @GET("search")
    fun searchStock(@Query("q") query: String): Flow<Resource<SearchResponse>>
}