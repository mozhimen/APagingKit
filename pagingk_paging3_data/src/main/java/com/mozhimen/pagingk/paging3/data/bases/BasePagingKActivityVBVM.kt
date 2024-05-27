package com.mozhimen.pagingk.paging3.data.bases

import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.elemk.androidx.appcompat.bases.viewbinding.BaseActivityVB
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.utilk.android.view.applyGone
import com.mozhimen.basick.utilk.android.view.applyVisible
import com.mozhimen.pagingk.paging3.data.commons.IPagingKActivity

/**
 * @ClassName BasePagingKActivityVBVM
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/26 22:51
 * @Version 1.0
 */
abstract class BasePagingKActivityVBVM<DES : Any, VB : ViewBinding, VM : BasePagingKViewModel<*, DES>> : BaseActivityVB<VB>(), IPagingKActivity<DES, VM> {

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    private val _basePagingKProxy by lazy { BasePagingKProxy<DES,VM>(this).apply { bindLifecycle(this@BasePagingKActivityVBVM) } }

    ////////////////////////////////////////////////////////////////////////

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    @CallSuper
    override fun initLayout() {
        super.initLayout()
        _basePagingKProxy.initLayout(this)
    }

    override fun onRefresh() {
        getPagingDataAdapter().refresh()
    }

    override fun onFirstLoadEmpty() {
        getRecyclerView().applyGone()
    }

    override fun onFirstLoadFinish() {
        getRecyclerView().applyVisible()
    }
}