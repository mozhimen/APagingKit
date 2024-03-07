package com.mozhimen.pagingk.test.countdown

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.pagingk.test.R

/**
 * @ClassName BaseVH
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/2/26 0:45
 * @Version 1.0
 */
// 基础ViewHolder
open class BaseViewHolder(itemView: View, onItemDeleteClick: IA_Listener<Int>?, private val timerHandler: Handler) : RecyclerView.ViewHolder(itemView), IUtilK {

    // 展示倒计时
    private val tvTimer = itemView.findViewById<TextView>(R.id.tv_time)

    // 删除按钮
    private val btnDelete = itemView.findViewById<TextView>(R.id.btn_delete)

    init {
        btnDelete.setOnClickListener {
            onItemDeleteClick?.invoke(adapterPosition)
        }
    }

    /**
     * 剩余倒计时
     */
    private var delay = 0L

    private val timerRunnable = Runnable {
        // 这里打印日志，来印证我们只跑了 "屏幕内可展示item数量的 倒计时"
//        Log.d(TAG, "run: ${hashCode()}")
        delay -= 1000
        updateTimerState()
    }

    // 开始倒计时
    private fun startTimer() {
        timerHandler.postDelayed(timerRunnable, 1000)
    }

    // 结束倒计时
    private fun endTimer() {
        timerHandler.removeCallbacks(timerRunnable)
    }

    // 检测倒计时 并 更新状态
    @SuppressLint("SetTextI18n")
    private fun updateTimerState() {
        if (delay <= 0) {
            // 倒计时结束了
            tvTimer.text = "已结束"
            itemView.setBackgroundColor(Color.GRAY)
            endTimer()
        } else {
            // 继续倒计时
            tvTimer.text = "${delay / 1000}S"
            itemView.setBackgroundColor(Color.parseColor("#FFBB86FC"))
            startTimer()
        }
    }

    /**
     * 进入屏幕时: 填充数据，这里声明为open，让子类重写
     */
    open fun onBindViewHolder(bean: CountDownBean) {
        Log.d(TAG, "onBindViewHolder: $adapterPosition")

        // 使用 终止时间 - 当前时间，计算倒计时还有多少秒
        delay = bean.terminalTime - System.currentTimeMillis()

        // 检测并更新timer状态
        updateTimerState()
    }

    /**
     * 滑出屏幕时: 移除倒计时
     */
    fun onViewRecycled() {
        Log.d(TAG, "onViewRecycled: $adapterPosition")

        // 终止计时
        endTimer()
    }
}
