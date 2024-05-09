package com.mozhimen.pagingk.paging3.data.mos

/**
 * @ClassName PagingKDataRes
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/28
 * @Version 1.0
 */
class PagingKDataRes<RES> {

    constructor(current: Int, pages: Int, size: Int, total: Int, records: List<RES>?) {
        this.currentPageIndex = current
        this.totalPageNum = pages
        this.pageSize = size
        this.totalItemNum = total
        this.currentPageItems = records
    }

    //////////////////////////////////////////////////////

    /**
     * current : 0
     * pages : 0
     * records : [{"context":"string","id":"string","isDel":0,"lastUpdateTime":"2020-10-29T07:59:25.286Z","likeCount":0,"lvl":0,"releaseTime":"2020-10-29T07:59:25.286Z","replyCommentId":"string","replyCount":0,"replyUserId":0,"score":0,"status":0,"subjectId":"string","subjectType":0,"userId":0}]
     * searchCount : true
     * size : 0
     * total : 0
     */
    var currentPageIndex = 0//当前页码
    var currentPageItems: List<RES>? = null//当前页条目
    var totalPageNum = 0//总页数
    var totalItemNum = 0//总条数
    var pageSize = 0//一页条数

    //////////////////////////////////////////////////////

    override fun toString(): String {
        return "PagingKDataRes(currentPageIndex=$currentPageIndex, totalPageNum=$totalPageNum, pageSize=$pageSize, totalItemNum=$totalItemNum, currentPageItems=$currentPageItems)"
    }
}