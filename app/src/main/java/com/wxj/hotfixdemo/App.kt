package com.wxj.hotfixdemo

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants

class App :TinkerApplication(
    ShareConstants.TINKER_ENABLE_ALL,
    "com.wxj.hotfixdemo.SampleApplication",
    "com.tencent.tinker.loader.TinkerLoader",
    false
){


}