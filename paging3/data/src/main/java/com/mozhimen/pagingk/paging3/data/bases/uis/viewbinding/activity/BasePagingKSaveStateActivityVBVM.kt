package com.mozhimen.pagingk.paging3.data.bases.uis.viewbinding.activity

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.mozhimen.basick.impls.proxys.IToolbarProxy
import com.mozhimen.basick.impls.proxys.IToolbarProxyProvider
import com.mozhimen.basick.impls.proxys.ToolbarProxy
import com.mozhimen.kotlin.elemk.commons.IA_Listener
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindViewLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.android.view.applyGone
import com.mozhimen.kotlin.utilk.android.view.applyVisible
import com.mozhimen.kotlin.utilk.androidx.appcompat.UtilKActionBar
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM
import com.mozhimen.pagingk.paging3.data.bases.uis.BasePagingKViewModel
import com.mozhimen.pagingk.paging3.data.commons.IPagingKActivity
import com.mozhimen.pagingk.paging3.data.impls.PagingKProxy
import com.mozhimen.uik.databinding.bases.viewbinding.activity.BaseSaveStateActivityVBVM

/**
 * @ClassName BaseBarPagingKActivityVDBVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/20
 * @Version 1.0
 */
@OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiCall_BindViewLifecycle::class)
abstract class BasePagingKSaveStateActivityVBVM<DES : Any, VB : ViewBinding, VM : BasePagingKViewModel<*, DES>> : BaseSaveStateActivityVBVM<VB, VM>(), IPagingKActivity<DES, VM> {

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiCall_BindViewLifecycle::class)
    private val _pagingKProxy by UtilKLazyJVM.lazy_ofNone { PagingKProxy<DES, VM>(this).apply { bindLifecycle(this@BasePagingKSaveStateActivityVBVM) } }

    ////////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun initLayout() {
        super.initLayout()
        _pagingKProxy.initLayout(this)
    }

    /////////////////////////////////////////////////////////////////////

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