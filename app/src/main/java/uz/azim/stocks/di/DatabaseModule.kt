package uz.azim.stocks.di

import androidx.room.Room
import uz.azim.stocks.App
import uz.azim.stocks.data.local.db.AppDatabase
import uz.azim.stocks.data.local.db.NewsDao
import uz.azim.stocks.data.local.db.StatsDao
import uz.azim.stocks.data.local.db.StockDao
import uz.azim.stocks.data.local.pref.StockPref

object DatabaseModule {

    private val database = provideDatabase()

    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(App.appInstance, AppDatabase::class.java, "uz.azim.stock_db")
            .build()
    }

    fun provideStockDao(): StockDao {
        return database.stockDao()
    }

    fun provideNewsDao(): NewsDao {
        return database.newsDao()
    }

    fun provideStatDao(): StatsDao {
        return database.statsDao()
    }

    fun providePref(): StockPref {
        return StockPref(App.appInstance)
    }

}
