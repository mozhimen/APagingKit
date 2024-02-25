package com.mozhimen.pagingk.test.countdown

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.pagingk.test.databinding.ActivityCountDownBinding

class CountDownActivity : BaseActivityVB<ActivityCountDownBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        // 添加测试数据
        val beans = ArrayList<CountDownBean>()
        for (i in 0..100) {
            // 计算终止时间，这里都是当前时间 + i乘以10s
            val terminalTime = System.currentTimeMillis() + i * 10_000
            // 这里手动计算了ViewType (i%2)+1
            beans.add(CountDownBean(i.toLong(), terminalTime, (i % 2) + 1))
        }

        val countDownAdapter = CountDownAdapter(beans)
        countDownAdapter.onItemDeleteClick = { position ->
            // 点击就删除
            beans.removeAt(position)
            countDownAdapter.notifyItemRemoved(position)
        }

        vb.countRecycler.layoutManager = LinearLayoutManager(this)
        vb.countRecycler.adapter = countDownAdapter
    }
}