package uz.azim.stocks.model


import com.google.gson.annotations.SerializedName

data class SearchResult(
    val description: String,
    val displaySymbol: String,
    val symbol: String,
    val type: String,
    val primary: List<String>
)