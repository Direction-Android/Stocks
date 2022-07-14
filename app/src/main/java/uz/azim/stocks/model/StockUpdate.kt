package uz.azim.stocks.model


import com.google.gson.annotations.SerializedName

data class StockUpdate(
    val `data`: List<Data>,
    val type: String
)