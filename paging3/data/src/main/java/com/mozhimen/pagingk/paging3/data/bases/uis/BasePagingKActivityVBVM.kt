package com.mozhimen.pagingk.paging3.data.bases.uis

import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.mozhimen.uik.databinding.bases.viewbinding.activity.BaseActivityVB
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.android.view.applyGone
import com.mozhimen.kotlin.utilk.android.view.applyVisible
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM
import com.mozhimen.pagingk.paging3.data.bases.BasePagingKProxy
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
    private val _basePagingKProxy by UtilKLazyJVM.lazy_ofNone { BasePagingKProxy<DES, VM>(this).apply { bindLifecycle(this@BasePagingKActivityVBVM) } }

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