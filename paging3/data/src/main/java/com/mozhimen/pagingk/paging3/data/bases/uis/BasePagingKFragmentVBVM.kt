package com.mozhimen.pagingk.paging3.data.bases.uis

import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.mozhimen.uik.databinding.bases.viewbinding.fragment.BaseFragmentVB
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindViewLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.android.view.applyGone
import com.mozhimen.kotlin.utilk.android.view.applyVisible
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM
import com.mozhimen.pagingk.paging3.data.commons.IPagingKActivity
import com.mozhimen.pagingk.paging3.data.impls.PagingKProxy

/**
 * @ClassName BasePagingKFragmentVBVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/16 15:05
 * @Version 1.0
 */
abstract class BasePagingKFragmentVBVM<DES : Any, VB : ViewBinding, VM : BasePagingKViewModel<*, DES>> : BaseFragmentVB<VB>(), IPagingKActivity<DES, VM> {

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiCall_BindViewLifecycle::class)
    private val _pagingKProxy by UtilKLazyJVM.lazy_ofNone { PagingKProxy<DES, VM>(this).apply { bindLifecycle(this@BasePagingKFragmentVBVM) } }

    ////////////////////////////////////////////////////////////////////////

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiCall_BindViewLifecycle::class)
    @CallSuper
    override fun initLayout() {
        super.initLayout()
        _pagingKProxy.initLayout(this.viewLifecycleOwner)
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