package com.mozhimen.pagingk.basic.commons

/**
 * @ClassName IPagingKRemoteMediator
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/9/20
 * @Version 1.0
 */
interface IPagingKRemoteMediatorDb<T : Any, D : IPagingKRemoteMediatorDbDao<T>> {
    fun getPagingKRemoteMediatorDbDao(): D
}