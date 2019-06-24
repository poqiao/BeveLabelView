# BeveLabelView
![image](https://github.com/poqiao/BeveLabelView/blob/master/app/src/main/res/mipmap-xhdpi/mode2.jpg)<br>
### 引入下面依赖</br>
```Java
   implementation 'com.github.poqiao:BeveLabelView:1.0.0'
```
```Java
        <com.mxq.pq.BeveLabelView
            android:layout_width="80dp"
            android:layout_height="80dp"
            app:label_corner="20dp"
            app:label_length="45dp"
            app:label_bg_color="#00ffae"
            app:label_mode="right_top"
            app:label_text="折扣"
            app:label_text_color="#020202"
            app:label_text_size="18sp" />
```
<br>可设置背景和文字颜色，文字大小，圆角大小，是否填满，上下左右位置</br>

## 控件属性
```java
   <!--斜角标签-->
    <declare-styleable name="BeveLabelView">
        <!--背景颜色-->
        <attr name="label_bg_color" format="color"/>
        <!--文字-->
        <attr name="label_text" format="string"/>
        <!-- 文字颜色-->
        <attr name="label_text_color" format="color"/>
        <!--文字大小-->
        <attr name="label_text_size" format="dimension"/>

        <attr name="label_length" format="dimension"/>
        <!--   圆角-->
        <attr name="label_corner" format="dimension"/>

        <attr name="label_mode">
            <!--   fill是沾满整个，-->
            <enum name="left_top" value="0"/>
            <enum name="right_top" value="1"/>
            <enum name="left_bottom" value="2"/>
            <enum name="right_bottom" value="3"/>
            <enum name="left_top_fill" value="4"/>
            <enum name="right_top_fill" value="5"/>
            <enum name="left_bottom_fill" value="6"/>
            <enum name="right_bottom_fill" value="7"/>
        </attr>
    </declare-styleable>
```
![image](https://github.com/poqiao/BeveLabelView/blob/master/app/src/main/res/mipmap-xhdpi/mode1.jpg)


