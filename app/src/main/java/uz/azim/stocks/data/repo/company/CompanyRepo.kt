package uz.azim.stocks.data.repo.company

import kotlinx.coroutines.flow.Flow
import uz.azim.stocks.model.News
import uz.azim.stocks.model.StockStat
import uz.azim.stocks.util.Resource


interface CompanyRepo {

    fun companyNews(
        symbol: String,
        fromDate: String,
        toDate: String
    ): Flow<Resource<List<News>>>


    fun getCompanyStats(symbol: String):Flow<Resource<List<StockStat>>>
}