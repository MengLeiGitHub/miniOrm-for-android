# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\work_ml\as_sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 7                         #设置混淆的压缩比率 0 ~ 7
-dontusemixedcaseclassnames                 # Aa aA
-dontskipnonpubliclibraryclasses             #如果应用程序引入的有jar包,并且想混淆jar包里面的class
-dontpreverify
-verbose                                       #混淆后生产映射文件 map 类名->转化后类名的映射
-optimizations !code/simplification/arithmetic,!field


##关于阿里的一些配置
#-keep class com.alibaba.sdk.android.**{*;}
#-keep class com.ut.**{*;}
#-keep class com.ta.**{*;}


# OkHttp3
#-dontwarn com.squareup.okhttp3.**
#-keep class com.squareup.okhttp3.** { *;}
#-dontwarn okio.**

# Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }

# OrmLite
# OrmLite
#-keepattributes *DatabaseField*
#-keepattributes *DatabaseTable*
#-keepattributes *SerializedName*
#-keep class com.j256.**
#-keepclassmembers class com.j256.** { *; }
#-keep enum com.j256.**
#-keepclassmembers enum com.j256.** { *; }
#-keep interface com.j256.**
#-keepclassmembers interface com.j256.** { *; }


##
-keep class com.miniorm.**
-keepclassmembers class com.miniorm.** { *; }
-keep enum com.miniorm.**
-keepclassmembers enum com.miniorm.** { *; }
-keep interface com.miniorm.**
-keepclassmembers interface com.miniorm.** { *; }


##
-keep class com.async.**
-keepclassmembers class com.async.** { *; }
-keep enum com.async.**
-keepclassmembers enum com.async.** { *; }
-keep interface com.async.**
-keepclassmembers interface com.async.** { *; }


##
-keep class com.uyin.**
-keepclassmembers class com.uyin.** { *; }
-keep enum com.uyin.**
-keepclassmembers enum com.uyin.** { *; }
-keep interface com.uyin.**
-keepclassmembers interface com.uyin.** { *; }

-dontoptimize
-dontpreverify



-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}




# Retrofit
#-dontwarn retrofit2.**
#-keep class retrofit2.** { *; }
#-keepattributes Signature
#-keepattributes Exceptions

# RxJava RxAndroid


# ButterKnife
#-keep class butterknife.** { *; }
#-dontwarn butterknife.internal.**
#-keep class **$$ViewBinder { *; }
#-keepclasseswithmembernames class * {
#    @butterknife.* <fields>;
#}
#-keepclasseswithmembernames class * {
#    @butterknife.* <methods>;
#}




# FastJson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*


# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}


 # Gson
 -keepattributes Signature
 -keepattributes *Annotation*
 -keep class sun.misc.Unsafe { *; }
 -keep class com.google.gson.stream.** { *; }
 # 使用Gson时需要配置Gson的解析对象及变量都不混淆。不然Gson会找不到变量。
 # 将下面替换成自己的实体类
 -keep class com.example.bean.** { *; }


# 避免混淆泛型
-keepattributes Signature


# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService


# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}



#-keep class com.dodola.rocoofix.** {*;}
#-keep class com.lody.legend.** {*;}
#-keepclassmembers class com.dodola.rocoosample.** {
#  public <init>();  #保留init,和include package保持一致
#}