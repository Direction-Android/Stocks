package uz.azim.stocks.di

import uz.azim.stocks.App
import uz.azim.stocks.data.local.db.AppDatabase
import uz.azim.stocks.data.local.pref.StockPref
import uz.azim.stocks.data.repo.company.CompanyRepo
import uz.azim.stocks.data.repo.company.CompanyRepoImpl
import uz.azim.stocks.data.repo.stock.StocksRepository
import uz.azim.stocks.data.repo.stock.StocksRepositoryImpl

object RepositoryModule {

    fun bindStockRepo(): StocksRepository = StocksRepositoryImpl(
        RetrofitModule.provideStockService(),
        RetrofitModule.provideSearchService(),
        AppDatabase.provideDatabase(App.appInstance).stockDao()
    )

    fun bindCompanyRepo(): CompanyRepo = CompanyRepoImpl(
        RetrofitModule.provideCompanyService(),
        AppDatabase.provideDatabase(App.appInstance).newsDao(),
        AppDatabase.provideDatabase(App.appInstance).statsDao(),
        DatabaseModule.providePref()
    )

}
