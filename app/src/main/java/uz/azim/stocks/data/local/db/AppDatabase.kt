package uz.azim.stocks.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.azim.stocks.model.News
import uz.azim.stocks.model.Quote
import uz.azim.stocks.model.StockStat

@Database(entities = [Quote::class, News::class, StockStat::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
    abstract fun newsDao(): NewsDao
    abstract fun statsDao(): StatsDao

    companion object {
        fun provideDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "stock_db")
                .build()
        }
    }

}
