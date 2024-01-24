package com.mozhimen.pagingk.test.restful.mos

/**
 * @ClassName DataRes
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/24 23:27
 * @Version 1.0
 */
data class DataRes(
    var apkLink: String? = null,
    val audit: Int = 0,
    var author: String? = null,
    val canEdit: Boolean = false,
    val chapterId: Int = 0,
    val chapterName: String? = null,
    val collect: Boolean = false,
    val courseId: Int = 0,
    val desc: String? = null,
    val descMd: String? = null,
    val envelopePic: String? = null,
    val fresh: Boolean = false,
    val id: Int = 0,
    val link: String? = null,
    val niceDate: String? = null,
    val niceShareDate: String? = null,
    val origin: String? = null,
    val prefix: String? = null,
    val projectLink: String? = null,
    val publishTime: Long = 0,
    val realSuperChapterId: Int = 0,
    val selfVisible: Int = 0,
    val shareDate: Long = 0,
    val shareUser: String? = null,
    val superChapterId: Int = 0,
    val superChapterName: String? = null,
    val title: String? = null,
    val type: Int = 0,
    val userId: Int = 0,
    val visible: Int = 0,
    val zan: Int = 0,
    val tags: List<TagRes>? = null
)