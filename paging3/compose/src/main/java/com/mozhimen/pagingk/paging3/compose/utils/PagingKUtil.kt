package com.mozhimen.pagingk.paging3.compose.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.paging.ItemSnapshotList
import androidx.paging.compose.LazyPagingItems
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.pagingk.paging3.compose.impls.LazyPagingItems2
import kotlinx.coroutines.launch

/**
 * @ClassName PagingKUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/10/15
 * @Version 1.0
 */
fun <T> ItemSnapshotList<T>.getPlaceholderList(): Collection<T?> =
    PagingKUtil.getPlaceholderList(this)

//////////////////////////////////////////////////////////////////////////

@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState =
    PagingKUtil.rememberLazyListState(this)

@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyGridState(): LazyGridState =
    PagingKUtil.rememberLazyGridState(this)

//////////////////////////////////////////////////////////////////////////

@Composable
fun <T : Any> LazyPagingItems2<T>.rememberLazyListState2(): LazyListState =
    PagingKUtil.rememberLazyListState2(this)

@Composable
fun <T : Any> LazyPagingItems2<T>.rememberLazyGridState2(): LazyGridState =
    PagingKUtil.rememberLazyGridState2(this)

//////////////////////////////////////////////////////////////////////////

@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListStateForever(): LazyListState =
    PagingKUtil.rememberLazyListStateForever(this)

@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyGridStateForever(): LazyGridState =
    PagingKUtil.rememberLazyGridStateForever(this)


//////////////////////////////////////////////////////////////////////////

object PagingKUtil : IUtilK {
    @JvmStatic
    fun <T> getPlaceholderList(itemSnapshotList: ItemSnapshotList<T>): Collection<T?> =
        (List<T?>(itemSnapshotList.placeholdersBefore) { null } + itemSnapshotList.items + List<T?>(itemSnapshotList.placeholdersAfter) { null }).also {
            UtilKLogWrapper.d(TAG, "getPlaceholderList: size ${it.size} placeholdersBefore ${itemSnapshotList.placeholdersBefore} placeholdersAfter ${itemSnapshotList.placeholdersAfter}")
        }

    //////////////////////////////////////////////////////////////////////////

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

    //////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @Composable
    fun <T : Any> rememberLazyListState2(lazyPagingItems2: LazyPagingItems2<T>): LazyListState {
        // After recreation, LazyPagingItems first return 0 items, then the cached items.
        // This behavior/issue is resetting the LazyListState scroll position.
        // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
        return when (lazyPagingItems2.itemCount) {
            // Return a different LazyListState instance.
            0 -> remember(this) { LazyListState(0, 0) }
            // Return rememberLazyListState (normal case).
            else -> androidx.compose.foundation.lazy.rememberLazyListState()
        }
    }

    @JvmStatic
    @Composable
    fun <T : Any> rememberLazyGridState2(lazyPagingItems2: LazyPagingItems2<T>): LazyGridState {
        // After recreation, LazyPagingItems first return 0 items, then the cached items.
        // This behavior/issue is resetting the LazyListState scroll position.
        // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
        return when (lazyPagingItems2.itemCount) {
            // Return a different LazyListState instance.
            0 -> remember(this) { LazyGridState(0, 0) }
            // Return rememberLazyListState (normal case).
            else -> androidx.compose.foundation.lazy.grid.rememberLazyGridState()
        }
    }

    //////////////////////////////////////////////////////////////////////////

    @Composable
    @JvmStatic
    fun <T : Any> rememberLazyListStateForever(lazyPagingItems: LazyPagingItems<T>): LazyListState {
        val state = androidx.compose.foundation.lazy.rememberLazyListState()
        val scrollIndex = rememberSaveable { mutableIntStateOf(0) }
        val coroutineScope = rememberCoroutineScope()
        // 重新创建后，LazyPagingItems 首先返回 0 个项目，然后返回缓存的项目。
        //此行为/问题是重置 LazyListState 滚动位置。
        return when (lazyPagingItems.itemCount) {
            // 返回不同的 LazyListState 实例
            0 -> rememberSaveable(saver = LazyListState.Saver) {
                scrollIndex.intValue = state.firstVisibleItemIndex
                LazyListState(state.firstVisibleItemIndex, state.firstVisibleItemScrollOffset)
            }

            else -> {
                if (scrollIndex.intValue in 1..<lazyPagingItems.itemCount) {
                    // 滚动后返回状态。
                    LaunchedEffect("") {
                        coroutineScope.launch {
                            state.animateScrollToItem(scrollIndex.intValue, state.firstVisibleItemScrollOffset)
                            scrollIndex.intValue = 0
                        }
                    }
                }
                state
            }
        }
    }

    @JvmStatic
    @Composable
    fun <T : Any> rememberLazyGridStateForever(lazyPagingItems: LazyPagingItems<T>): LazyGridState {
        val state = androidx.compose.foundation.lazy.grid.rememberLazyGridState()
        val scrollIndex = rememberSaveable { mutableIntStateOf(0) }
        val coroutineScope = rememberCoroutineScope()
        // 重新创建后，LazyPagingItems 首先返回 0 个项目，然后返回缓存的项目。
        //此行为/问题是重置 LazyListState 滚动位置。
        return when (lazyPagingItems.itemCount) {
            // 返回不同的 LazyListState 实例
            0 -> rememberSaveable(saver = LazyGridState.Saver) {
                scrollIndex.intValue = state.firstVisibleItemIndex
                LazyGridState(state.firstVisibleItemIndex, state.firstVisibleItemScrollOffset)
            }

            else -> {
                if (scrollIndex.intValue in 1..<lazyPagingItems.itemCount) {
                    // 滚动后返回状态。
                    LaunchedEffect("") {
                        coroutineScope.launch {
                            state.animateScrollToItem(scrollIndex.intValue, state.firstVisibleItemScrollOffset)
                            scrollIndex.intValue = 0
                        }
                    }
                }
                state
            }
        }
    }
}