package com.mozhimen.pagingk.paging3.compose.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.paging.compose.LazyPagingItems

/**
 * @ClassName PagingKUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/10/15
 * @Version 1.0
 */
@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState =
    PagingKUtil.rememberLazyListState(this)

@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyGridState(): LazyGridState =
    PagingKUtil.rememberLazyGridState(this)

//////////////////////////////////////////////////////////////////////////

object PagingKUtil {
    @JvmStatic
    @Composable
    fun <T : Any> rememberLazyListState(lazyPagingItems: LazyPagingItems<T>): LazyListState {
        // After recreation, LazyPagingItems first return 0 items, then the cached items.
        // This behavior/issue is resetting the LazyListState scroll position.
        // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
        return when (lazyPagingItems.itemCount) {
            // Return a different LazyListState instance.
            0 -> remember(this) { LazyListState(0, 0) }
            // Return rememberLazyListState (normal case).
            else -> androidx.compose.foundation.lazy.rememberLazyListState()
        }
    }

    @JvmStatic
    @Composable
    fun <T : Any> rememberLazyGridState(lazyPagingItems: LazyPagingItems<T>): LazyGridState {
        // After recreation, LazyPagingItems first return 0 items, then the cached items.
        // This behavior/issue is resetting the LazyListState scroll position.
        // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
        return when (lazyPagingItems.itemCount) {
            // Return a different LazyListState instance.
            0 -> remember(this) { LazyGridState(0, 0) }
            // Return rememberLazyListState (normal case).
            else -> androidx.compose.foundation.lazy.grid.rememberLazyGridState()
        }
    }
}