package com.mozhimen.pagingk.basic.cons

sealed interface SPageState {
    data class Refresh(val page: Int) : SPageState
    data class Prepend(val page: Int?) : SPageState
    data class Append(val page: Int?) : SPageState
}