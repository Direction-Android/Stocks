package uz.azim.stocks.ui.fragment.search.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.azim.stocks.R
import uz.azim.stocks.databinding.ItemSearchBinding
import uz.azim.stocks.model.SearchResult
import uz.azim.stocks.util.comparator.SearchResultComparator
import uz.azim.stocks.util.getColor
import uz.azim.stocks.util.getDrawable
import uz.azim.stocks.util.inflate

class SearchAdapter :
    ListAdapter<SearchResult, SearchAdapter.SearchVH>(SearchResultComparator()) {

    private var stockClickListener: ((String) -> Unit)? = null
    fun onStockClickListener(listener: ((String) -> Unit)?) {
        stockClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVH {
        return SearchVH(parent.inflate(R.layout.item_search))
    }

    override fun onBindViewHolder(holder: SearchVH, position: Int) {
        holder.onBind(getItem(position), position)
    }

    inner class SearchVH(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemSearchBinding.bind(view)
        private val errorImg = getDrawable(view.context, R.drawable.error_image)
        private val oceanColor = getColor(view.context, R.color.item_bg)
        private val whiteColor = getColor(itemView.context, R.color.white)

        @SuppressLint("SetTextI18n")
        fun onBind(quote: SearchResult, position: Int) {
            binding.apply {
                val cardColor = if (position % 2 == 0) oceanColor else whiteColor
                stockRoot.setCardBackgroundColor(cardColor)
                imgStock.setStockImg(quote.symbol, errorImg)
                tvStockName.text = quote.symbol
                tvStockCompanyName.text = quote.description
                stockRoot.setOnClickListener {
                    stockClickListener?.invoke(quote.symbol)
                }
            }
        }
    }

}
