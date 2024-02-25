package com.mozhimen.pagingk.test.countdown

import android.annotation.SuppressLint
import android.os.Handler
import android.view.View
import android.widget.TextView
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.pagingk.test.R

/**
 * @ClassName OnSaleVH
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/2/26 0:46
 * @Version 1.0
 */
// 继承自BaseViewHolder，因为有公共的倒计时套件
class OnSaleViewHolder(itemView: View, onItemDeleteClick: IA_Listener<Int>?, timerHandler: Handler) : BaseViewHolder(itemView, onItemDeleteClick, timerHandler) {
    // 添加了一个名字
    private val tvName = itemView.findViewById<TextView>(R.id.tv_name)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(bean: CountDownBean) {
        super.onBindViewHolder(bean)
        // 添加名字
        tvName.text = "${bean.id} 在售"
    }
}
