package uz.azim.stocks.util.vmfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.DeleteFromFavouritesUseCase
import uz.azim.stocks.domain.GetFavouritesUseCase
import uz.azim.stocks.ui.vm.FavouritesFragmentViewModel

class FavouritesFragmentViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val stocksRepository = RepositoryModule.bindStockRepo()
        return FavouritesFragmentViewModel(
            GetFavouritesUseCase(stocksRepository),
            DeleteFromFavouritesUseCase(stocksRepository)
        ) as T
    }
}