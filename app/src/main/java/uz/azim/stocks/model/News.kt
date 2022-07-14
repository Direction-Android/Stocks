package uz.azim.stocks.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    @PrimaryKey
    val id: Int,
    val category: String,
    val datetime: Int,
    val headline: String,
    val image: String,
    val related: String,
    val source: String,
    val summary: String,
    val url: String,
    var symbol: String = ""
)