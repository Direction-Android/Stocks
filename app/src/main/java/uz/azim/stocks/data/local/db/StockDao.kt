package uz.azim.stocks.data.local.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.azim.stocks.model.Quote

@Dao
interface StockDao {

    @Query("SELECT * FROM quote_table")
    fun getAllFavoriteStocks(): Flow<List<Quote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStock(quote: Quote)

    @Delete
    suspend fun deleteStock(quote: Quote)

    @Update
    suspend fun updateStock(quote: Quote)
}