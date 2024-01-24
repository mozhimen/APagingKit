package com.mozhimen.pagingk.test.paging

import com.mozhimen.pagingk.test.restful.mos.DataRes

/**
 * @ClassName IOnUpdateListener
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/24 23:40
 * @Version 1.0
 */
typealias IOnItemUpdateListener = (Int, DataRes?, DataResPagingDataAdapter) -> Unit