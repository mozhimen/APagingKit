package com.mozhimen.pagingk.paging3.data.bases.uis

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.mozhimen.basick.helpers.IToolbarProxy
import com.mozhimen.basick.helpers.IToolbarProxyProvider
import com.mozhimen.basick.helpers.ToolbarProxy
import com.mozhimen.kotlin.elemk.commons.IA_Listener
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindViewLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.androidx.appcompat.UtilKActionBar
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM

/**
 * @ClassName BaseBarPagingKActivityVDBVM
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/20
 * @Version 1.0
 */
@OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiCall_BindViewLifecycle::class)
abstract class BaseBarPagingKActivityVBVM<DES : Any, VB : ViewBinding, VM : BasePagingKViewModel<*, DES>> : BasePagingKActivityVBVM<DES, VB, VM>, IToolbarProxyProvider, IToolbarProxy {
    /**
     * 针对Hilt(@JvmOverloads kotlin默认参数值无效)
     * @constructor
     */
    constructor() : super()

    /////////////////////////////////////////////////////////////////////

    protected val toolBarProxy by UtilKLazyJVM.lazy_ofNone { ToolbarProxy<BaseBarPagingKActivityVBVM<*, *, *>>().apply { bindLifecycle(this@BaseBarPagingKActivityVBVM) } }

    /////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun initLayout() {
        super.initLayout()
        UtilKActionBar.get_ofSupport(this)?.let {
            toolBarProxy.initToolbar(this, it)
        }
    }

    /////////////////////////////////////////////////////////////////////

    override fun setToolbarNavigationIconRes(intResDrawable: Int) {
        toolBarProxy.setToolbarNavigationIconRes(intResDrawable)
    }

    override fun <A> setToolbarNavigationOnClickListener(provider: A, listener: IA_Listener<A>) where A : AppCompatActivity, A : IToolbarProxyProvider {
        toolBarProxy.setToolbarNavigationOnClickListener(provider, listener)
    }

    override fun setToolbarBackground(drawable: Drawable) {
        toolBarProxy.setToolbarBackground(drawable)
    }

    override fun setToolbarBackgroundColor(intColor: Int) {
        toolBarProxy.setToolbarBackgroundColor(intColor)
    }

    override fun setToolbarBackgroundRes(intResDrawable: Int) {
        toolBarProxy.setToolbarBackgroundRes(intResDrawable)
    }

    override fun setToolbarText(strText: CharSequence) {
        toolBarProxy.setToolbarText(strText)
    }

    override fun setToolbarTextRes(intStrRes: Int) {
        toolBarProxy.setToolbarTextRes(intStrRes)
    }

    override fun setToolbarCustomView(customView: View) {
        toolBarProxy.setToolbarCustomView(customView)
    }
}