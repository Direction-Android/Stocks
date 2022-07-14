package uz.azim.stocks.util.comparator

import androidx.recyclerview.widget.DiffUtil
import uz.azim.stocks.model.Quote

class StockComparator : DiffUtil.ItemCallback<Quote>() {
    override fun areItemsTheSame(oldItem: Quote, newItem: Quote) = oldItem.symbol == newItem.symbol

    override fun areContentsTheSame(oldItem: Quote, newItem: Quote) = oldItem == newItem
}