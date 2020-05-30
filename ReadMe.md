# 友盟GameAnalytics（非应用）

### 介绍

1. 官网sdk下载地址：https://developer.umeng.com/sdk/u3d
2. sdk版本选用8.0.0以上：
   - 安卓sdk： 建议使用unitypackage里自带的版本 （自己下载新版本，数据不会上传到后台，坑）
   - iOS sdk：  建议使用unitypackage里自带的版本 （自己下载新版本，XCode打包会报错，坑）
   - Unity桥接sdk：v3.2.0(更新日期：2019年1月2日)

### 初步接入

初步接入参考官网：https://developer.umeng.com/docs/66632/detail/67588

##### Unity

1. 场景内新建GameObject并添加上UmengMono.cs脚本
2. 在合适的地方埋点 调用UmengMgr声明的方法即可

##### 安卓

1. AndroidManifest.xml增加权限（ 权限必须加上 不然没有数据）

   ```java
   <!--检测联网方式，区分用户设备使用的是2G、3G或是WiFi-->
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
   <!--访问互联网-->
   <uses-permission android:name="android.permission.INTERNET" />
   <!--获取用户手机的IMEI，用来唯一的标识用户   只有这个会弹窗提醒 获取手机状态和电话权限-->
   <uses-permission android:name="android.permission.READ_PHONE_STATE" />
   <!--如果您的应用会运行在无法读取IMEI的平板上，我们会将mac地址作为用户的唯一标识-->
   <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
   ```

##### iOS：

需要导出后,在xcode里手动添加依赖库 （这是官方文档写的 经测试 不需要添加也行 仅靠unitypackage里自带的自动化导出即可）

```
TARGETS`—>`Build Phases`—>`Link Binary With Libraries`—> `+` —>`libz.tdb
TARGETS`—>`Build Phases`—>`Link Binary With Libraries`—> `+` —>`libsqlite3.tbd
```

### 高级功能

高级功能参考官网：https://developer.umeng.com/docs/119267/detail/118637

##### 页面统计

1.通过功能使用=>页面访问路径集成

四种页面采集模式对比 Auto、Manual、Legacy_Auto、Legacy_Manual。

Auto模式会自动采集安卓原生页面 比如UnityPlayerActivity或者自写的MainActivity   iOS正常

Legacy_Manual模式不会自动采集页面  需要PageBegin和PageEnd成对采集  iOS同理

因此Unity集成的友盟不需要显示原生页面  应该选用Legacy_Manual模式 而友盟默认使用Legacy_Auto 所以需要调用SDK里的安卓原生代码 这样页面访问统计就能正常使用了。[我的aar源码](https://github.com/yoyohan1/AndroidJar_UmengUtil)

```
MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL);
```

2.通过功能使用=>自定义事件集成

采用计算事件（非多参数类型事件）传入时长即可

后台创建计算事件 然后代码传入时长 即可统计到后台自定义事件 可以查看均值和总值

```
/// <summary>
/// 自定义事件 — 计算事件数 可用于页面统计
/// </summary>
public void Event(string eventId, Dictionary<string, string> attributes, int value)
{
    Debug.Log("友盟统计计算事件Event  eventID：" + eventId + " value:" + value);
    GA.Event(eventId, attributes, value);
}
```

##### 自定义事件

1.多参数类型事件示例

```
Map<String, Object> music = new HashMap<String, Object>();
music.put("music_type", "popular");//自定义参数：音乐类型，值：流行
music.put("singer", "JJ"); //歌手：(林俊杰)JJ
music.put("song_name","A_Thousand_Years_Later"); //歌名：一千年以后
music.put("song_price",100); //价格：100元
MobclickAgent.onEventObject(this, "play_music", music);
```

参数值可以是如下几种类型之一：**String**、**Long**、**Integer**、**Float**、**Double**、**Short**。

不同的参数类型对应不同的计算方式，总共可以分为两大类，数值型和字符型

- 数字型：支持累加值、最大值、最小值、平均值和去重数计算
- 字符型：支持去重数计算

2.计算事件示例  参考上述页面访问统计即可