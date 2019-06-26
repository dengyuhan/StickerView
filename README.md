## 说明
对其[ wuapnjie/StickerView ](https://github.com/wuapnjie/StickerView)进行了扩展

部分API与原项目不同，请谨慎使用

## 增加的特性
1. `TextSticker`支持宽高自适应
2. 支持`Gravity`，不再需要`Sticker.Position`
3. 增加长按事件
4. 支持指定初始化缩放

## 截图
<img src="screenshots/screenshot.gif" width="200"/>

## Gradle
```
implementation 'com.dyhdyh.support:sticker:1.6.14'
```

## 添加贴纸时指定位置
```
stickerView.addSticker(new DrawableSticker(drawable), Gravity.BOTTOM | Gravity.LEFT);
```

## 初始化缩放
```
<com.xiaopo.flying.sticker.StickerView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:initialScale="0.5"
    app:initialScaleBaseline="parent_width"/>
    
<!--初始化时的缩放-->
<attr name="initialScale" format="float" />
<!--初始化时的缩放 以宽度还是以高度为基准-->
<attr name="initialScaleBaseline" format="enum" >
    <enum name="self" value="0"/>
    <enum name="parent_width" value="1"/>
    <enum name="parent_height" value="2"/>
</attr>
```