package uz.azim.stocks.model

data class BaseResponse(
    val meta:Meta,
    val data: StocksResponse
)
