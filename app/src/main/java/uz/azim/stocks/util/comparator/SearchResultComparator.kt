package uz.azim.stocks.util.comparator

import androidx.recyclerview.widget.DiffUtil
import uz.azim.stocks.model.Quote
import uz.azim.stocks.model.SearchResult

class SearchResultComparator : DiffUtil.ItemCallback<SearchResult>() {
    override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult) = oldItem.symbol == newItem.symbol

    override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult) = oldItem == newItem
}