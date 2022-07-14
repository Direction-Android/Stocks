package uz.azim.stocks.model

data class StocksResponse(
    val start: Int,
    val count: Int,
    val total: Int,
    val description: String,
    val quotes: List<Quote>
)