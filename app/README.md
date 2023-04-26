# ScrollLayout

## Abstract 摘要

在ScrollView或者ListView里面使用ViewPager.支持手势上拉滑出,中途停顿,下滑退出页面,类似百度地图内场景抽屉拖拽效果效果

## Gif 动画

![ScorllLayout.gif](https://github.com/R-Gang/RVCardGallery/blob/main/art/ScorllLayout.gif)

#### Function and parameter definitions 功能与参数定义

<table>
  <tbody>
    <tr>
		<td align="center">一级</td>
		<td align="center">ScrollLayout</td>
    </tr>
    <tr>
        <td align="center">二级</td>
        <td align="center">ContentRecyclerView</td>
        <td align="center">ContentListView</td>
        <td align="center">ContentScrollView</td>
    </tr>
  </tbody>
</table>

<table>
  <tdead>
    <tr>
      <th align="center">配置参数</th>
      <th align="center">参数含义</th>
    </tr>
  </tdead>
  <tbody>
    <tr>
      <td align="center">allowHorizontalScroll</td>
      <td align="center">是否支持横向滚动</td>
    </tr>
    <tr>
      <td align="center">exitOffset</td>
      <td align="center">最低部退出状态时可看到的高度，0为不可见</td>
    </tr>
    <tr>
      <td align="center">isSupportExit</td>
      <td align="center">是否支持下滑退出，支持会有下滑到最底部时的回调</td>
    </tr>
    <tr>
      <td align="center">maxOffset</td>
      <td align="center">打开状态时内容显示区域的高度</td>
    </tr>
    <tr>
      <td align="center">minOffset</td>
      <td align="center">关闭状态时最上方预留高度</td>
    </tr>
    <tr>
      <td align="center">mode</td>
      <td align="center">位置状态，关闭、打开、底部</td>
    </tr>
  </tbody>
</table>

#### In layout

```
	    <com.gang.scroll.ScrollLayout
            android:id="@+id/scroll_down_layout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="#000000"

            app:allowHorizontalScroll="true"  //是否支持横向滚动
            app:exitOffset="0dp"              //最低部退出状态时可看到的高度，0为不可见
            app:isSupportExit="true"	      //是否支持下滑退出，支持会有下滑到最底部时的回调
            app:maxOffset="260dp"             //打开状态时内容显示区域的高度
            app:minOffset="50dp"              //关闭状态时最上方预留高度
            app:mode="open">                  //默认位置状态，关闭、打开、底部

```

### or

#### In Code

```
	{
	    mScrollLayout.setMinOffset(0);
        mScrollLayout.setMaxOffset(800);
        mScrollLayout.setExitOffset(500);
        mScrollLayout.setToOpen();
        mScrollLayout.setIsSupportExit(true);
        mScrollLayout.setAllowHorizontalScroll(true);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
    }

```

## Other 其它

依赖内包含重写的ContentScrollView、ContentListView与ContentRecyclerView
可在ScrollLayout里面里面使用ViewPager等功能，配合使用效果更佳。 感谢[Ted](https://github.com/xiongwei-git)的库给的方向。

## Reference

[使用RecyclerView实现Gallery画廊效果](https://www.jianshu.com/p/85bf072bfeed)
[Gallery画廊效果](https://github.com/huazhiyuan2008/RecyclerViewCardGallery)
[类似百度地图抽屉拖拽效果](https://github.com/yingLanNull/ScrollLayout)

### ScreenShoot ViewToImage

[各种view转bitmap](https://www.jianshu.com/p/3d03c66cf169)
[ViewToImage](https://github.com/huazhiyuan2008/ViewToImage)
