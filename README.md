# tinkerDemo

第一步
这个分支是采用tinker的jar工具来打出差异包，jar工具的话，可以直接下载tinker官方代码，然后运行gradlew buildTinkerSdk这个任务，这样编译成功就会在工程目录的
buildSdk文件夹下生成build文件夹，
然后把这个文件夹里面的工具都复制出去，复制到你的电脑的某一个文件夹里面，假设在d:/build/apk里面

第二步
然后修改你的项目代码，修改androidManifest.xml文件里面的<meta-data android:name="TINKER_ID" android:value="8"/>
这里对应的value就是你的tinker_id，然后编译出一个老包，再随便改动一些东西，修改tinker_id大于8，再编译出一个新包，
把这两个apk都复制到d:/build/apk里面

第三步
这个时候再修改tinker_config.xml文件，把里面的
<loader value="tinker.sample.android.SampleApplication"/>
这一行修改成你自己的application路径

然后还需要修改最下边的签名
<issue id="sign">
        <!--the signature file path, in window use \, in linux use /, and the default path is the running location-->
        <path value="release.keystore"/>
        <!--storepass-->
        <storepass value="testres"/>
        <!--keypass-->
        <keypass value="testres"/>
        <!--alias-->
        <alias value="testres"/>
    </issue>
这里也要改成你自己的签名信息

注意：有时候会编译失败，是因为腾讯的loader文件夹不在主dex文件中，这个时候需要添加
<allowLoaderInAnyDex value="true" />
<removeLoaderForAllDex value="true" />
把这两行属性添加到<issue id="property"></issur>属性里

其实这个tinker_config.xml文件很重要，里面还可以配置sevenZipPath的路径，其实也就是你电脑里面的7za压缩路径

第四步
修改tinker_proguard.pro文件，把-applymapping ./app-release-mapping.txt放开，这里的app-release-mapping.txt就是你的老包的mapping文件，然后最下边的
-keep class tinker.sample.android.app.SampleApplication {
    *;
}
改成你自己的application路径
然后继续修改tinker_multidexkeep.pro
#your dex.loader patterns here
-keep class tinker.sample.android.app.SampleApplication {
    <init>();
}
这里改成你自己的application
  
修改完成之后，就开始使用命令打差异包了，命令如下
java -jar tinker-patch-cli.jar -old old.apk -new new.apk -config tinker_config.xml -out output_path
打包成功的话，会生成output_path路径，里面会有签名成功的.apk，这就是你的差异包，然后直接使用即可







    
