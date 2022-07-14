package uz.azim.stocks.util.retrofit

import kotlinx.coroutines.*
import retrofit2.*
import uz.azim.stocks.util.Resource
import java.lang.reflect.Type

class ResourceCallAdapter<R>(
    private val responseType: Type
) : CallAdapter<R, Call<Resource<R>>> {

    override fun responseType() = responseType

    @ExperimentalCoroutinesApi
    override fun adapt(call: Call<R>) = ResultCall(call)
}