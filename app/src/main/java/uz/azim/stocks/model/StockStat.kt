package uz.azim.stocks.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_statistics")
data class StockStat(
    val `actual`: Double,
    val estimate: Double,
    val period: String,
    val symbol: String,
    @PrimaryKey(autoGenerate = true)
    var id:Int
)