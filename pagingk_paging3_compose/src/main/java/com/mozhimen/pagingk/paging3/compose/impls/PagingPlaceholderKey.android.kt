package com.mozhimen.pagingk.paging3.compose.impls

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

/**
 * @ClassName PagingPlaceholderKey
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/10/20 15:45
 * @Version 1.0
 */
internal fun getPagingPlaceholderKey(index: Int): Any = PagingPlaceholderKey(index)

@SuppressLint("BanParcelableUsage")
private data class PagingPlaceholderKey(private val index: Int) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(index)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<PagingPlaceholderKey> =
            object : Parcelable.Creator<PagingPlaceholderKey> {
                override fun createFromParcel(parcel: Parcel) =
                    PagingPlaceholderKey(parcel.readInt())

                override fun newArray(size: Int) = arrayOfNulls<PagingPlaceholderKey?>(size)
            }
    }
}
