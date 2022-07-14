package uz.azim.stocks.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.azim.stocks.App
import uz.azim.stocks.data.remote.CompanyService
import uz.azim.stocks.data.remote.SearchService
import uz.azim.stocks.data.remote.StocksService
import uz.azim.stocks.util.FINN_BASE_URL
import uz.azim.stocks.util.MBOUM_BASE_URL
import uz.azim.stocks.util.retrofit.FlowResourceCallAdapterFactory
import java.util.concurrent.TimeUnit

object RetrofitModule {

    private val retrofit = Retrofit.Builder().addConverterFactory(provideConverterFactory())
        .addCallAdapterFactory(FlowResourceCallAdapterFactory())
        .client(provideRetrofitClient())

    private fun provideRetrofitClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(ChuckerInterceptor.Builder(App.appInstance).build())
        client.addInterceptor { chain ->
            chain.run {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader("X-Mboum-Secret", "demo")
                        .addHeader("X-Finnhub-Token", "c15r63v48v6oal0lqon0")
                        .build()
                )
            }
        }
        return client.build()
    }

    private fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    //For trending stocks I used MBOUM because they provide data with pagination.
    //FINNHUB provide all data at once(They were too much), so I decided to use MBOUM
    fun provideStockService(): StocksService {
        return retrofit
            .baseUrl(MBOUM_BASE_URL)
            .build()
            .create(StocksService::class.java)
    }

    fun provideSearchService(): SearchService {
        return retrofit
            .baseUrl(FINN_BASE_URL)
            .build()
            .create(SearchService::class.java)
    }

    fun provideCompanyService(): CompanyService {
        return retrofit
            .baseUrl(FINN_BASE_URL)
            .build()
            .create(CompanyService::class.java)
    }
}
