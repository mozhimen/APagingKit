package com.mozhimen.pagingk.test

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.pagingk.test.countdown.CountDownActivity
import com.mozhimen.pagingk.test.crud.CrudActivity
import com.mozhimen.pagingk.test.databinding.ActivityMainBinding
import com.mozhimen.pagingk.test.paging.PagingActivity

class MainActivity : BaseActivityVDB<ActivityMainBinding>() {
    fun goCountDown(view: View) {
        startContext<CountDownActivity>()
    }

    fun goCrud(view: View) {
        startContext<CrudActivity>()
    }

    fun goPaging(view: View) {
        startContext<PagingActivity>()
    }
}