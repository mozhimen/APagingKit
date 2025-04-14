package com.mozhimen.pagingk.paging3.data.bases.uis.viewbinding.fragment

import android.view.View
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.mozhimen.kotlin.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindViewLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.android.view.applyGone
import com.mozhimen.kotlin.utilk.android.view.applyVisible
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM
import com.mozhimen.pagingk.paging3.data.bases.uis.BasePagingKViewModel
import com.mozhimen.pagingk.paging3.data.commons.IPagingKActivity
import com.mozhimen.pagingk.paging3.data.impls.PagingKProxy
import com.mozhimen.uik.databinding.bases.viewbinding.fragment.BaseDialogFragmentVBVMVMSelf
import com.mozhimen.uik.databinding.bases.viewbinding.fragment.BaseFragmentVBVMVMSelf
import com.mozhimen.uik.databinding.bases.viewdatabinding.fragment.BaseFragmentVDBVMVMSelf

/**
 * @ClassName BasePagingKFragmentVBVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/16 15:05
 * @Version 1.0
 */
abstract class BasePagingKDialogFragmentVBVMVMSelf<DES : Any, VB : ViewBinding, VMSELF : BasePagingKViewModel<*, DES>, VMSHARE : BaseViewModel> : BaseDialogFragmentVBVMVMSelf<VB, VMSHARE, VMSELF>(),
    IPagingKActivity<DES, VMSELF> {

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiCall_BindViewLifecycle::class)
    private val _pagingKProxy by UtilKLazyJVM.lazy_ofNone { PagingKProxy<DES, VMSELF>(this).apply { bindLifecycle(this@BasePagingKDialogFragmentVBVMVMSelf) } }

    ////////////////////////////////////////////////////////////////////////

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiCall_BindViewLifecycle::class)
    @CallSuper
    override fun initLayout() {
        super.initLayout()
        _pagingKProxy.initLayout(this.viewLifecycleOwner)
    }

    ////////////////////////////////////////////////////////////////////////

    override fun getViewModel(): VMSELF {
        return vmSelf
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