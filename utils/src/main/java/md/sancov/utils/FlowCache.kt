package md.sancov.utils

import kotlinx.coroutines.flow.*

data class FlowCache<T>(private val flow: Flow<State<T>>) {
    private val cache = MutableStateFlow<State<T>?>(null)

    fun value(): T? {
        return cache.value?.data
    }

    fun resolve() = flow {
        val cached = cache.value

        if (cached != null && cached !is State.Error) {
            emit(cached)
        } else {
            val source = flow.onEach {
                cache.emit(it)
            }
            emitAll(source)
        }
    }

    fun reset() {
        cache.value = null
    }
}