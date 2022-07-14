package uz.azim.stocks.data.local.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.azim.stocks.model.News
import uz.azim.stocks.model.Quote

@Dao
interface NewsDao {

    @Query("SELECT * FROM news WHERE symbol == :symbol")
    fun getAllNews(symbol:String): Flow<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNews(news: List<News>)

    @Query("DELETE FROM news")
    suspend fun deleteNews()

}