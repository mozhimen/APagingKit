package com.mozhimen.pagingk.test.crud

/**
 * @ClassName SampleViewEvents
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/2/26 0:06
 * @Version 1.0
 */
sealed class SampleViewEvents {
    data class Edit(val sampleEntity: SampleEntity) : SampleViewEvents()
    data class Remove(val sampleEntity: SampleEntity) : SampleViewEvents()
    object InsertItemHeader : SampleViewEvents()
    object InsertItemFooter : SampleViewEvents()
}