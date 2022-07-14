package uz.azim.stocks.ui.fragment.stocks.listener

import android.widget.ImageView
import uz.azim.stocks.Stock
import uz.azim.stocks.model.Quote

interface OnStockClickListener {
    fun onFavoriteClick(quote: Quote, imgFav: ImageView)

    fun onStockClick(quote: Quote)
}