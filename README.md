## 说明
对其[ wuapnjie/StickerView ](https://github.com/wuapnjie/StickerView)进行了扩展

### 增加的特性
1. 支持`ViewSticker`
2. 支持`Gravity`，不再需要`Sticker.Position`

## 截图
<img src="screenshots/screenshot.gif" width="200"/>

## Gradle
```
implementation 'com.dyhdyh.support:sticker:1.6.0'
```

## 添加贴纸时指定位置
```
stickerView.addSticker(new DrawableSticker(drawable), Gravity.BOTTOM | Gravity.LEFT);
```

## 以View作为贴纸
#### 在Java中添加ViewSticker
```
ImageView imageView = new ImageView(this);
final StickerView.LayoutParams layoutParams = new StickerView.LayoutParams(300, 300);
layoutParams.gravity = Gravity.LEFT;//靠左
imageView.setLayoutParams(layoutParams);
stickerView.addView(imageView);
```
#### 在XML中添加ViewSticker
设置子View属性`app:sticker="true"`，就可以作为贴纸，否则就是普通的View  
设置子View属性`android:layout_gravity="right|bottom`，可以指定添加的位置

```
<com.xiaopo.flying.sticker.StickerView
    android:id="@+id/sticker_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:bringToFrontCurrentSticker="true"
    app:showBorder="true"
    app:showIcons="true">

    <ImageView
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:scaleType="centerCrop"
        android:src="@drawable/test"
        android:layout_gravity="right|bottom"
        app:sticker="true" />

    <ImageView
        app:sticker="false"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:src="@drawable/haizewang_90" />

</com.xiaopo.flying.sticker.StickerView>
```