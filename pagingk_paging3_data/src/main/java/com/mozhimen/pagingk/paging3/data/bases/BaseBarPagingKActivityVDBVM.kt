package com.mozhimen.pagingk.paging3.data.bases

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import androidx.databinding.ViewDataBinding
import com.mozhimen.basick.helpers.IToolbarProxyProvider
import com.mozhimen.basick.helpers.ToolbarProxy
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.lintk.optins.OApiUse_BaseApplication
import com.mozhimen.kotlin.utilk.androidx.appcompat.UtilKActionBar
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM

/**
 * @ClassName BaseBarPagingKActivityVDBVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/20
 * @Version 1.0
 */
abstract class BaseBarPagingKActivityVDBVM<DES : Any, VDB : ViewDataBinding, VM : BasePagingKViewModel<*, DES>> : BasePagingKActivityVDBVM<DES, VDB, VM>, IToolbarProxyProvider {
    /**
     * 针对Hilt(@JvmOverloads kotlin默认参数值无效)
     * @constructor
     */
    constructor() : super()

    /////////////////////////////////////////////////////////////////////
    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiUse_BaseApplication::class)
    private val _toolBarProxy by UtilKLazyJVM.lazy_ofNone { ToolbarProxy<BaseBarPagingKActivityVDBVM<*, *, *>>().apply { bindLifecycle(this@BaseBarPagingKActivityVDBVM) } }

    /////////////////////////////////////////////////////////////////////

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiUse_BaseApplication::class)
    @CallSuper
    override fun initLayout() {
        super.initLayout()
        UtilKActionBar.get_ofSupport(this)?.let {
            _toolBarProxy.initToolbar(this, it, title)
        }
    }

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiUse_BaseApplication::class)
    override fun setToolbarTitle(strTitle: CharSequence) {
        _toolBarProxy.setToolbarTitle(strTitle)
    }

    @OptIn(OApiUse_BaseApplication::class, OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    override fun setToolbarTitle(intResStr: Int) {
        _toolBarProxy.setToolbarTitle(getString(intResStr))
    }

    override fun getToolbarNavigationIcon(): Int =
        android.R.drawable.arrow_up_float

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiUse_BaseApplication::class)
    override fun setToolbarNavigationIcon(@DrawableRes intResDrawable: Int) {
        _toolBarProxy.setToolbarNavigationIcon(intResDrawable)
    }

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiUse_BaseApplication::class)
    override fun setToolbarCustomView(customView: View) {
        _toolBarProxy.setToolbarCustomView(customView)
    }
}