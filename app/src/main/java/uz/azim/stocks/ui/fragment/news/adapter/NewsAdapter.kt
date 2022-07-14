package uz.azim.stocks.ui.fragment.news.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.azim.stocks.R
import uz.azim.stocks.databinding.ItemNewsBinding
import uz.azim.stocks.model.News
import uz.azim.stocks.util.comparator.NewsComparator
import uz.azim.stocks.util.getDrawable
import uz.azim.stocks.util.inflate
import uz.azim.stocks.util.setImageRemote

class NewsAdapter : ListAdapter<News, NewsAdapter.NewsVH>(NewsComparator()) {

    private var onClickListener: ((News) -> Unit)? = null

    fun onNewsClickListener(listener: ((News) -> Unit)) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        return NewsVH(parent.inflate(R.layout.item_news))
    }

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class NewsVH(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemNewsBinding.bind(view)
        private val errImg = getDrawable(view.context, R.drawable.error_image)

        fun onBind(news: News) {
            binding.apply {
                imgNews.setImageRemote(news.image, errImg)
                tvHeadline.text = news.headline
                tvSummary.text = news.summary
                tvSource.text = news.source
                newsContainer.setOnClickListener {
                    onClickListener?.invoke(news)
                }
            }
        }
    }

}
