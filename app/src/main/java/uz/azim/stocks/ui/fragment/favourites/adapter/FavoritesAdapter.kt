package uz.azim.stocks.ui.fragment.favourites.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.azim.stocks.R
import uz.azim.stocks.databinding.ItemStockBinding
import uz.azim.stocks.model.Quote
import uz.azim.stocks.ui.fragment.stocks.listener.OnStockClickListener
import uz.azim.stocks.util.*
import uz.azim.stocks.util.comparator.StockComparator

class FavoritesAdapter :
    ListAdapter<Quote, FavoritesAdapter.FavoritesVH>(StockComparator()) {

    private var stockClickListener: OnStockClickListener? = null
    fun onStockClickListener(listener: OnStockClickListener) {
        stockClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVH {
        return FavoritesVH(parent.inflate(R.layout.item_stock))
    }

    override fun onBindViewHolder(holder: FavoritesVH, position: Int) {
        holder.onBind(getItem(position), position)
    }

    inner class FavoritesVH(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemStockBinding.bind(view)
        private val errorImg = getDrawable(view.context, R.drawable.error_image)
        private val imgFavor = getDrawable(view.context, R.drawable.ic_star_active)
        private val imgUnFav = getDrawable(view.context, R.drawable.ic_star_inactive)
        private val redColor = getColor(view.context, R.color.red)
        private val greenColor = getColor(view.context, R.color.green)
        private val oceanColor = getColor(view.context, R.color.item_bg)
        private val whiteColor = getColor(itemView.context, R.color.white)

        @SuppressLint("SetTextI18n")
        fun onBind(quote: Quote, position: Int) {
            binding.apply {
                val cardColor = if (position % 2 == 0) oceanColor else whiteColor
                val textColor = if (quote.regularMarketChange < 0) redColor else greenColor
                if (quote.isFavorite)
                    imgFav.setImageDrawable(imgFavor)
                else
                    imgFav.setImageDrawable(imgUnFav)
                stockRoot.setCardBackgroundColor(cardColor)
                imgStock.setStockImg(quote.symbol, errorImg)
                tvStockName.text = quote.symbol
                tvStockCompanyName.text = quote.shortName
                tvDiff.text = "$${quote.regularMarketChange.decimalsAfter(2)} (${
                    quote.regularMarketChangePercent.decimalsAfter(2)
                }%)"
                tvDiff.setTextColor(textColor)
                tvStockPrice.text = quote.regularMarketPrice.formatMoney()
                imgFav.setOnClickListener {
                    stockClickListener?.onFavoriteClick(quote, imgFav)
                }
                stockRoot.setOnClickListener {
                    stockClickListener?.onStockClick(quote)
                }
            }
        }
    }

}
