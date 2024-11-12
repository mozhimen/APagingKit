package com.mozhimen.pagingk.basic.commons

/**
 * @ClassName IPaginKDataSource
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/12 15:42
 * @Version 1.0
 */
interface IPagingKDataSource<RES, DES> {
    /**
     * 添加其他数据(组合数据)
     * @param isLoadInitial 是否在初始化中调用
     * @param hasMore 是否还有更多
     * @param datas 数据
     */
    suspend fun onCombineData(currentPageIndex: Int?, datas: MutableList<DES>){}

    /**
     * 数据聚合 将请求回来的原始数据，组装成目标数据
     * @param datas 请求回来的数据
     */
    suspend fun onTransformData(currentPageIndex: Int?, datas: List<RES>): List<DES>

    /**
     * 获取头部数据
     */
    suspend fun onGetHeader(): List<DES>? = null

    /**
     * 获取底部数据
     */
    suspend fun onGetFooter():  List<DES>? = null
}