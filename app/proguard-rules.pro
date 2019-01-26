## Add project specific ProGuard rules here.
## You can control the set of applied configuration files using the
## proguardFiles setting in build.gradle.
##
## For more details, see
##   http://developer.android.com/guide/developing/tools/proguard.html
#
## If your project uses WebView with JS, uncomment the following
## and specify the fully qualified class name to the JavaScript interface
## class:
##-keepclassmembers class fqcn.of.javascript.interface.for.webview {
##   public *;
##}
#
## Uncomment this to preserve the line number information for
## debugging stack traces.
##-keepattributes SourceFile,LineNumberTable
#
## If you keep the line number information, uncomment this to
## hide the original source file name.
##-renamesourcefileattribute SourceFile
#
#
##1.基本混淆
## 代码混淆压缩比，在0和7之间，默认为5，一般不需要改
#-optimizationpasses 5
#
## 混淆时不使用大小写混合，混淆后的类名为小写
#-dontusemixedcaseclassnames
#
## 指定不去忽略非公共的库的类
#-dontskipnonpubliclibraryclasses
#
## 指定不去忽略非公共的库的类的成员
#-dontskipnonpubliclibraryclassmembers
#
## 不做预校验，preverify是proguard的4个步骤之一
## Android不需要preverify，去掉这一步可加快混淆速度
#-dontpreverify
#
## 有了verbose这句话，混淆后就会生成映射文件
## 包含有类名->混淆后类名的映射关系
## 然后使用printmapping指定映射文件的名称
#-verbose
#-printmapping proguardMapping.txt
#
## 指定混淆时采用的算法，后面的参数是一个过滤器
## 这个过滤器是谷歌推荐的算法，一般不改变
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#
## 保护代码中的Annotation不被混淆，这在JSON实体映射时非常重要，比如fastJson
#-keepattributes *Annotation*
#
## 避免混淆泛型，这在JSON实体映射时非常重要，比如fastJson
#-keepattributes Signature
#
##抛出异常时保留代码行号，在异常分析中可以方便定位
#-keepattributes SourceFile,LineNumberTable
#
##用于告诉ProGuard，不要跳过对非公开类的处理。默认情况下是跳过的，因为程序中不会引用它们，
##有些情况下人们编写的代码与类库中的类在同一个包下，并且对包中内容加以引用，此时需要加入此条声明。
#-dontskipnonpubliclibraryclasses
#
##这个是给Microsoft Windows用户的，因为ProGuard假定使用的操作系统是能区分两个只是大小写不同的文件名，
##但是Microsoft Windows不是这样的操作系统，所以必须为ProGuard指定-dontusemixedcaseclassnames选项
#-dontusemixedcaseclassnames
#
#
##2.需要保留的东西
## 保留所有的本地native方法不被混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
## 保留了继承自Activity、Application这些类的子类
## 因为这些子类，都有可能被外部调用
## 比如说，第一行就保证了所有Activity的子类不要被混淆
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class * extends android.view.View
#-keep public class com.android.vending.licensing.ILicensingService
#
## 如果有引用android-support-v4.jar包，可以添加下面这行
#-keep public class com.xxxx.app.ui.fragment.** {*;}
#
## 保留在Activity中的方法参数是view的方法，
## 从而我们在layout里面编写onClick就不会被影响
#-keepclassmembers class * extends android.app.Activity {
#    public void *(android.view.View);
#}
#
## 枚举类不能被混淆
#-keepclassmembers enum * {
#public static **[] values();
#public static ** valueOf(java.lang.String);
#}
#
## 保留自定义控件（继承自View）不被混淆
#-keep public class * extends android.view.View {
#    *** get*();
#    void set*(***);
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#
## 保留Parcelable序列化的类不被混淆
#-keep class * implements android.os.Parcelable {
#    public static final android.os.Parcelable$Creator *;
#}
#
## 保留Serializable序列化的类不被混淆
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#
## 对于R（资源）下的所有类及其方法，都不能被混淆
#-keep class **.R$* {
#    *;
#}
#
## 对于带有回调函数onXXEvent的，不能被混淆
#-keepclassmembers class * {
#    void *(**On*Event);
#}
#
#
#
##3.针对app量身定制
#
##a.保留实体类和成员不被混淆，对于实体保留它的get、set方法和boolean的get方法，有人喜欢用isXXX的方式，所以不要遗漏
## 一种好的做法是把所有实体都放在一个包下进行管理，这样只写一次混淆就够了，
##避免以后在别的包中新增的实体而忘记保留，代码在混淆后因为找不到相应的实体类而崩溃。
#-keep public class com.zmj.mvp.testsocket.bean.bean.**{
#    public void set*(***);
#    public ** get*();
#    public *** is*();
#}
#
##b.内嵌类
##内嵌类经常会被混淆，结果在调用的时候为空就崩溃了，
##最好的解决方法就是把这个内嵌类拿出来，单独成为一个类。如果一定要内置，那么这个类就必须在混淆的时候保留，比如如下：
#-keep class com.zmj.mvp.testsocket.bean.LoginResult$*{*;}
##这个$符号就是用来分割内嵌类与其母体的标志。
#
##c.对webView的处理
#-keepclassmembers class * extends android.webkit.webViewClient{
#     public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
#     public boolean *(android.webkit.WebView, java.lang.String);
#}
#-keepclassmembers class * extends android.webkit.webViewClient {
#    public void *(android.webkit.webView, java.lang.String);
#}
#
##d.对javaScript的处理
## 保留JS方法不被混淆
#-keepclassmembers class com.example.xxx.MainActivity$JSInterface1 {#其中JSInterface是MainActivity的子类
#    <methods>;
#}
#
##e.处理反射
##在程序中使用SomeClass.class.method这样的静态方法，在ProGuard中是在压缩过程中被保留的，
##那么对于Class.forName("SomeClass")呢，SomeClass不会被压缩过程中移除，它会检查程序中使用的Class.forName方法，
##对参数SomeClass法外开恩，不会被移除。但是在混淆过程中，无论是Class.forName("SomeClass")，还是SomeClass.class，
##都不能蒙混过关，SomeClass这个类名称会被混淆，因此，我们要在ProGuard.cfg文件中保留这个类名称。
##Class.forName("SomeClass")
##SomeClass.class
##SomeClass.class.getField("someField")
##SomeClass.class.getDeclaredField("someField")
##SomeClass.class.getMethod("someMethod", new Class[] {})
##SomeClass.class.getMethod("someMethod", new Class[] { A.class })
##SomeClass.class.getMethod("someMethod", new Class[] { A.class, B.class })
##SomeClass.class.getDeclaredMethod("someMethod", new Class[] {})
##SomeClass.class.getDeclaredMethod("someMethod", new Class[] { A.class })
##SomeClass.class.getDeclaredMethod("someMethod", new Class[] { A.class, B.class })
##AtomicIntegerFieldUpdater.newUpdater(SomeClass.class, "someField")
##AtomicLongFieldUpdater.newUpdater(SomeClass.class, "someField")
##AtomicReferenceFieldUpdater.newUpdater(SomeClass.class, SomeType.class, "someField")
##在混淆的时候，要在项目中搜索一下上述方法，将相应的类或者方法的名称进行保留而不被混淆。
#
#
##f.对于自定义View的解决方案
##但凡在Layout目录下的XML布局文件配置的自定义View，都不能进行混淆。为此要遍历Layout下的所有的XML布局文件，
##找到那些自定义View，然后确认其是否在ProGuard文件中保留。有一种思路是，在我们使用自定义View时，前面都必须加上我们的包名，
##比如com.a.b.customeview，我们可以遍历所有Layout下的XML布局文件，查找所有匹配com.a.b的标签即可。
#
#
##4.针对第三方jar包的解决方案
##我们在Android项目中不可避免要使用很多第三方提供的SDK，一般而言，这些SDK是经过ProGuard混淆的，
##而我们所需要做的就是避免这些SDK的类和方法在我们APP被混淆。
#
##a.针对android-support-v4.jar的解决方案



# 代码混淆压缩比，在0~7之间
-optimizationpasses 5
# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify
-verbose
# 避免混淆泛型
-keepattributes Signature

# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses
#google推荐算法
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
# 避免混淆Annotation、内部类、泛型、匿名类
-keepattributes *Annotation*,InnerClasses,Signature,EnclosingMethod
# 重命名抛出异常时的文件名称
-renamesourcefileattribute SourceFile
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
# 处理support包
-dontnote android.support.**
-dontwarn android.support.**
# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {*;}
# 保留四大组件，自定义的Application等这些类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}
# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * implements java.io.Serializable {
   static final long serialVersionUID;
   private static final java.io.ObjectStreamField[]   serialPersistentFields;
   private void writeObject(java.io.ObjectOutputStream);
   private void readObject(java.io.ObjectInputStream);
   java.lang.Object writeReplace();
   java.lang.Object readResolve();
}
#assume no side effects:删除android.util.Log输出的日志
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}
#保留Keep注解的类名和方法
-keep,allowobfuscation @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclassmembers class * {
    @android.support.annotation.Keep *;
}
#3D 地图 V5.0.0之前：

-dontwarn com.amap.api.**
-dontwarn com.autonavi.**
-keep class com.amap.api.**{*;}
-keep class com.autonavi.**{*;}

-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.amap.mapcore.*{*;}
-keep   class com.amap.api.trace.**{*;}

#3D 地图 V5.0.0之后：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.**{*;}
-keep   class com.amap.api.trace.**{*;}

#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

#搜索
-keep   class com.amap.api.services.**{*;}

#2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}

#导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

#fastjson混淆
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.**{*;}
-keep class com.alibaba.fastjson.**{*; }
-keep public class com.ninstarscf.ld.model.entity.**{*;}
