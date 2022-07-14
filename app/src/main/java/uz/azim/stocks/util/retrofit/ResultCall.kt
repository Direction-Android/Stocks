package uz.azim.stocks.util.retrofit

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.azim.stocks.util.Resource
import uz.azim.stocks.util.log

class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, Resource<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<Resource<T>>) = proxy.enqueue(object : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            log("$response")
            val result = if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    Resource.Success<T>(it)
                }
            } else {
                Resource.Error(response.message())
            }

            callback.onResponse(this@ResultCall, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val result = Resource.Error<T>(t.message.toString())

            callback.onResponse(this@ResultCall, Response.success(result))
        }
    })

    override fun cloneImpl() = ResultCall(proxy.clone())

}

abstract class CallDelegate<TIn, TOut>(
    protected val proxy: Call<TIn>
) : Call<TOut> {
    override fun execute(): Response<TOut> = throw NotImplementedError()
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    final override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun cloneImpl(): Call<TOut>
    override fun timeout() = proxy.timeout()
}