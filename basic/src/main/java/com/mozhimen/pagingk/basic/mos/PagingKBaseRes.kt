package com.mozhimen.pagingk.basic.mos

/**
 * @ClassName BasePaingKRep
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/10/11 17:02
 * @Version 1.0
 */
class PagingKBaseRes<RES> {

    companion object {
        @JvmStatic
        fun <RES> empty(): PagingKBaseRes<RES> =
            PagingKBaseRes(0, null)
    }

    //////////////////////////////////////////////////////

    constructor()

    constructor(code: Int, msg: String?) {
        this.code = code
        this.msg = msg
    }

    constructor(code: Int, msg: String?, data: PagingKDataRes<RES>) {
        this.code = code
        this.msg = msg
        this.data = data
    }

    //////////////////////////////////////////////////////

    /**
     * code : 1
     * msg : 查询成功
     * data : [{"id":"3","name":"我的","sortNum":2,"parentId":0,"remark":"底部菜单","status":1,"isDel":0,"createUser":"cjx","createTime":"2020-10-15 10:36:02"},{"id":"2","name":"排行榜","sortNum":1,"parentId":0,"remark":"底部菜单","status":1,"isDel":0,"createUser":"cjx","createTime":"2020-10-15 10:35:47"},{"id":"1","name":"发现","sortNum":0,"parentId":0,"remark":"底部菜单","status":1,"isDel":0,"createUser":"cjx","createTime":"2020-10-15 10:35:26"}]
     */
    var code = 0
    var msg: String? = null
    var data: PagingKDataRes<RES>? = null

    //////////////////////////////////////////////////////

    fun isSuccessful(): Boolean {
        return code == 1
    }

    override fun toString(): String {
        return "PagingKBaseRes(code=$code, msg=$msg, data=$data)"
    }
}