package uz.azim.stocks.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class InitialState<T>:Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}
