package uz.azim.stocks.data.local.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.azim.stocks.model.News
import uz.azim.stocks.model.Quote
import uz.azim.stocks.model.StockStat

@Dao
interface StatsDao {

    @Query("SELECT * FROM stock_statistics WHERE symbol == :symbol")
    fun getStats(symbol:String): Flow<List<StockStat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStats(news: List<StockStat>)

    @Query("DELETE FROM stock_statistics")
    suspend fun deleteStats()

}