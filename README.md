# RVCard-Gallery

RecyclerView实现Card Gallery效果，替代ViewPager方案。能够快速滑动并最终定位到居中位置

![RVCard-Gallery.gif](https://github.com/R-Gang/RVCardGallery/blob/main/art/RVCard-Gallery_blur.gif)

引入方式：

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }

    dependencies {
          // 画廊（v0.1.6@aar）
         implementation 'com.github.R-Gang:RVCardGallery:latest.integration'
    }

## Usage

调用`new PageScaleHelper().attachToRecyclerView(mRecyclerView);`扩展RecyclerView
```
final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
mRecyclerView.setLayoutManager(linearLayoutManager);
mRecyclerView.setAdapter(new CardAdapter());
// mRecyclerView绑定scale效果
new CardScaleHelper().attachToRecyclerView(mRecyclerView);
```

在adapter相应的位置调用
```
mCardAdapterHelper.onCreateViewHolder(parent, itemView);
mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
```

## GPU Render测试图
![RVCard-Gallery_GPU.gif](https://github.com/R-Gang/RVCardGallery/blob/main/art/RVCard-Gallery_GPU.gif)
