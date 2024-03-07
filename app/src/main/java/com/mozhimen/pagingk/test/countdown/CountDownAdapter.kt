package com.mozhimen.pagingk.test.countdown

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.basick.elemk.commons.IA_Listener
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.pagingk.test.R

/**
 * @ClassName Adapter
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/2/26 0:47
 * @Version 1.0
 */
class CountDownAdapter(private val datas: List<CountDownBean>) : RecyclerView.Adapter<BaseViewHolder>(), IUtilK {


    /**
     * 点击item的事件
     */
    var onItemDeleteClick: IA_Listener<Int>? = null

    /**
     * ViewHolder的工厂
     */
    private val vhs = SparseArray<VHFactory>()

    /**
     * 用来执行倒计时
     */
    private val timerHandler = Handler(Looper.getMainLooper())

    /////////////////////////////////////////////////////////////////////////////////

    /**
     * 初始化工厂
     */
    init {
        vhs.put(ItemType.ITEM_BASE, BaseVHFactory())
        vhs.put(ItemType.ITEM_ON_SALE, OnSaleVHFactory())
    }

    // 直接从工厂map中获取对应的工厂调用createVH()方法即可
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        vhs.get(viewType).onCreateViewHolder(parent.context, parent, onItemDeleteClick, timerHandler)

    // 滑入屏幕内调用，直接使用hoder.display()展示数据
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBindViewHolder(datas[position])
    }

    override fun getItemCount(): Int =
        datas.size

    // ViewHolder滑出屏幕调用，进行回收
    override fun onViewRecycled(holder: BaseViewHolder) {
        holder.onViewRecycled()
    }

    /**
     * 根据数据类型返回ViewType
     */
    override fun getItemViewType(position: Int): Int =
        datas[position].type

    /**
     * 定义抽象工厂
     */
    abstract class VHFactory {
        abstract fun onCreateViewHolder(context: Context, parent: ViewGroup, onItemDeleteClick: IA_Listener<Int>?, timerHandler: Handler): BaseViewHolder
    }

    /**
     * BaseViewHolder工厂
     */
    inner class BaseVHFactory : VHFactory() {
        override fun onCreateViewHolder(context: Context, parent: ViewGroup, onItemDeleteClick: IA_Listener<Int>?, timerHandler: Handler): BaseViewHolder {
            return BaseViewHolder(LayoutInflater.from(context).inflate(R.layout.item_count_down_base, parent, false), onItemDeleteClick, timerHandler)
        }
    }

    /**
     * OnSaleVH工厂
     */
    class OnSaleVHFactory : VHFactory() {
        override fun onCreateViewHolder(context: Context, parent: ViewGroup, onItemDeleteClick: IA_Listener<Int>?, timerHandler: Handler): BaseViewHolder {
            return OnSaleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_count_down_on_sale, parent, false), onItemDeleteClick, timerHandler)
        }
    }
}
