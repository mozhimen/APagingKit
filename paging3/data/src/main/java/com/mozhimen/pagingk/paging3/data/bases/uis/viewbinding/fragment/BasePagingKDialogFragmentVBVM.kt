package com.mozhimen.pagingk.paging3.data.bases.uis.viewbinding.fragment

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindViewLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.android.view.applyGone
import com.mozhimen.kotlin.utilk.android.view.applyVisible
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM
import com.mozhimen.pagingk.paging3.data.bases.uis.BasePagingKViewModel
import com.mozhimen.pagingk.paging3.data.commons.IPagingKActivity
import com.mozhimen.pagingk.paging3.data.impls.PagingKProxy
import com.mozhimen.uik.databinding.bases.viewbinding.fragment.BaseDialogFragmentVBVM
import com.mozhimen.uik.databinding.bases.viewbinding.fragment.BaseFragmentVBVM
import com.mozhimen.uik.databinding.bases.viewdatabinding.fragment.BaseFragmentVDBVM

/**
 * @ClassName BasePagingKFragmentVBVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/16 15:05
 * @Version 1.0
 */
abstract class BasePagingKDialogFragmentVBVM<DES : Any, VB : ViewBinding, VM : BasePagingKViewModel<*, DES>> : BaseDialogFragmentVBVM<VB, VM>(), IPagingKActivity<DES, VM> {

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiCall_BindViewLifecycle::class)
    private val _pagingKProxy by UtilKLazyJVM.lazy_ofNone { PagingKProxy<DES, VM>(this).apply { bindLifecycle(this@BasePagingKDialogFragmentVBVM) } }

    ////////////////////////////////////////////////////////////////////////

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiCall_BindViewLifecycle::class)
    @CallSuper
    override fun initLayout() {
        super.initLayout()
        _pagingKProxy.initLayout(this.viewLifecycleOwner)
    }

    ////////////////////////////////////////////////////////////////////////

    override fun getViewModel(): VM {
        return vm
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