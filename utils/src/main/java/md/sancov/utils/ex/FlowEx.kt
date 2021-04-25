package md.sancov.utils.ex

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import md.sancov.utils.State

inline fun <T, reified R> Flow<State<T>>.mapState(
        crossinline transform: suspend (value: T?) -> R
): Flow<State<R>> {
    return map { it.map(transform) }
}