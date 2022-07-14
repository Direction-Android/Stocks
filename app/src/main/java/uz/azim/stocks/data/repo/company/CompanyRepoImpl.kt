package uz.azim.stocks.data.repo.company

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import uz.azim.stocks.data.local.db.NewsDao
import uz.azim.stocks.data.local.db.StatsDao
import uz.azim.stocks.data.local.pref.StockPref
import uz.azim.stocks.data.remote.CompanyService
import uz.azim.stocks.model.News
import uz.azim.stocks.model.StockStat
import uz.azim.stocks.util.NetworkBoundResource
import uz.azim.stocks.util.Resource
import kotlin.time.ExperimentalTime
import kotlin.time.days

class CompanyRepoImpl constructor(
    private val companyService: CompanyService,
    private val newsDao: NewsDao,
    private val statsDao: StatsDao,
    private val pref: StockPref
) : CompanyRepo {

    companion object {
        @ExperimentalTime
        private val DATA_EXPIRY_IN_MILLIS = 1.days.inMilliseconds.toLong()
    }

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    override fun companyNews(
        symbol: String,
        fromDate: String,
        toDate: String
    ) = object : NetworkBoundResource<List<News>, List<News>>() {
        override fun fetchFromLocal(): Flow<List<News>> {
            return newsDao.getAllNews(symbol)
        }

        override fun fetchFromRemote(): Flow<Resource<List<News>>> {
            return companyService.companyNews(symbol, fromDate, toDate)
        }

        override suspend fun saveRemoteData(data: List<News>) {
            data.forEach { news ->
                news.symbol = symbol
            }
            newsDao.deleteNews()
            newsDao.saveNews(data)
            pref.saveSyncedTime(System.currentTimeMillis())
        }

        override fun shouldFetchFromRemote(data: List<News>): Boolean {
            val lastSynced = pref.getLastSyncedTime()
            return lastSynced == -1L || data.isNullOrEmpty() || isExpired(lastSynced)
        }

    }.asFlow().flowOn(Dispatchers.IO)

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    override fun getCompanyStats(symbol: String) =
        object : NetworkBoundResource<List<StockStat>, List<StockStat>>() {
            override fun fetchFromLocal(): Flow<List<StockStat>> {
                return statsDao.getStats(symbol)
            }

            override fun fetchFromRemote(): Flow<Resource<List<StockStat>>> {
                return companyService.stockStat(symbol)
            }

            override suspend fun saveRemoteData(data: List<StockStat>) {
                statsDao.deleteStats()
                statsDao.saveStats(data)
                pref.saveSyncedTimeStats(System.currentTimeMillis())
            }

            override fun shouldFetchFromRemote(data: List<StockStat>): Boolean {
                val lastSynced = pref.getLastSyncedTime()
                return lastSynced == -1L || data.isNullOrEmpty() || isExpired(lastSynced)
            }

        }.asFlow()

    @ExperimentalTime
    private fun isExpired(lastSynced: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastSynced) >= DATA_EXPIRY_IN_MILLIS
    }
}
