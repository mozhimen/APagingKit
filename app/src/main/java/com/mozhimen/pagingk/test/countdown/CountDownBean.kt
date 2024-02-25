package com.mozhimen.pagingk.test.countdown

/**
 * @ClassName BaseItemBean
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/2/26 0:45
 * @Version 1.0
 */
// 注意这里的terminalTime，指的指终止时间，不是时间差，是一个时间值。
// type可以理解为ViewType，当然中间有对应关系的
data class CountDownBean(val id: Long, var terminalTime: Long, val type: Int)
