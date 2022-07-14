package uz.azim.stocks.ui.fragment.stocks.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.azim.stocks.R
import uz.azim.stocks.databinding.LoadingFooterBinding
import uz.azim.stocks.util.inflate

class LoadFooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadFooterAdapter.VenuesLoadFooterVH>() {

    override fun onBindViewHolder(holder: VenuesLoadFooterVH, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): VenuesLoadFooterVH {
        return VenuesLoadFooterVH(parent.inflate(R.layout.loading_footer))
    }

    inner class VenuesLoadFooterVH(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = LoadingFooterBinding.bind(view)
        fun bind(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState !is LoadState.Loading
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

    }
}