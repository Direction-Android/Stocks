package uz.azim.stocks.ui.fragment.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.azim.stocks.ui.fragment.favourites.FavouritesFragment
import uz.azim.stocks.ui.fragment.stocks.StocksFragment

class StocksPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount()=2

    override fun createFragment(position: Int) = if (position == 0)
        StocksFragment()
    else
        FavouritesFragment()

}