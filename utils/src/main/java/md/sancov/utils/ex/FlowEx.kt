package md.sancov.utils.ex

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import md.sancov.utils.State

suspend inline fun <T, reified R> Flow<State<T>>.mapState(
        crossinline transform: (value: T?) -> R
): Flow<State<R>> {
    return map { it.map(transform) }
}

suspend inline fun <T, reified R> Flow<State<T>>.mapNotNullState(
        crossinline transform: (value: T?) -> R
): Flow<State<R>> {
    return mapNotNull { it.map(transform) }
}