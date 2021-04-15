package md.sancov.utils

sealed class State<T> {
    data class Success<T>(val value: T): State<T>()
    data class Loading<T>(val value: T?): State<T>()
    data class Error<T>(val cause: Throwable, val value: T?): State<T>()

    val data: T? get() = when (this) {
        is Success -> value
        is Loading -> value
        is Error -> value
    }

    val isLoading: Boolean get() = this is Loading
    val isError: Boolean get() = this is kotlin.Error

    inline fun<reified T1> map(crossinline transform: (value: T?) -> T1): State<T1> {
        val transformed = transform(data)

        return when (this) {
            is Success -> Success(transformed)
            is Loading -> Loading(transformed)
            is Error -> Error(cause, transformed)
        }
    }
}