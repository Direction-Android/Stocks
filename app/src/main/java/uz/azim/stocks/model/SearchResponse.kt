package uz.azim.stocks.model


data class SearchResponse(
    val count: Int,
    val result: List<SearchResult>
)