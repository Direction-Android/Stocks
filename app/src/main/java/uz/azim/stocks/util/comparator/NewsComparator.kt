package uz.azim.stocks.util.comparator

import androidx.recyclerview.widget.DiffUtil
import uz.azim.stocks.model.News

class NewsComparator : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: News, newItem: News) = oldItem == newItem
}