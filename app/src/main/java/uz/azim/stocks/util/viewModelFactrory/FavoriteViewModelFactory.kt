package uz.azim.stocks.util.viewModelFactrory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.azim.stocks.di.RepositoryModule
import uz.azim.stocks.domain.stocksUseCases.DeleteStockUseCase
import uz.azim.stocks.domain.stocksUseCases.GetAllFavsUseCase
import uz.azim.stocks.domain.stocksUseCases.SearchStockUseCase
import uz.azim.stocks.ui.fragment.favourites.vm.FavoriteVM
import uz.azim.stocks.ui.fragment.search.vm.SearchVM

class FavoriteViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteVM::class.java)) {
            val stockRepo = RepositoryModule.bindStockRepo()
            return FavoriteVM(GetAllFavsUseCase(stockRepo), DeleteStockUseCase(stockRepo)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}