package com.gang.rvcardgallery

import android.app.Application
import com.gang.imageloader.initImage.initLoadImage
import com.gang.tools.kotlin.utils.initToolsUtils

/**
 *
 * @ProjectName:    RVCardGallery
 * @Package:        com.gang.rvcardgallery
 * @ClassName:      MyApplication
 * @Description:     java类作用描述
 * @Author:         haoruigang
 * @CreateDate:     2022/1/4 14:31
 * @UpdateUser:     更新者：
 * @UpdateDate:     2022/1/4 14:31
 * @UpdateRemark:   更新说明：
 * @Version:
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initLoadImage(this)
        initToolsUtils(this)
    }

    override fun onTerminate() {
        super.onTerminate()

    }
}