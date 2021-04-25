package md.sancov.utils

typealias EmptyState = State<Unit>

sealed class State<out T> {
    data class Success<T>(val value: T): State<T>()
    data class Loading<T>(val value: T? = null): State<T>()
    data class Error<T>(val cause: Throwable, val value: T? = null): State<T>()

    val data: T? get() = when (this) {
        is Success -> value
        is Loading -> value
        is Error -> value
    }

    val dropData: State<Unit> get() = when (this) {
        is Success -> Success(Unit)
        is Loading -> Loading(Unit)
        is Error -> Error(cause, Unit)
    }

    val isSuccess: Boolean get() = this is Success
    val isLoading: Boolean get() = this is Loading
    val isError: Boolean get() = this is kotlin.Error

    suspend inline fun<reified R> map(crossinline transform: suspend (value: T?) -> R): State<R> {
        val transformed = transform(data)

        return when (this) {
            is Success -> Success(transformed)
            is Loading -> Loading(transformed)
            is Error -> Error(cause, transformed)
        }
    }
}