package uz.azim.stocks.ui.fragment.quote.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class QuoteDetailsAdapter(fragment: Fragment, private val list: ArrayList<Fragment>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = list.size

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}