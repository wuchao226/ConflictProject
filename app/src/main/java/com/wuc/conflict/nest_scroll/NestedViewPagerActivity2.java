package com.wuc.conflict.nest_scroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.wuc.conflict.R;
import com.wuc.conflict.databinding.ActivityMainBinding;
import com.wuc.conflict.databinding.ActivityNestedViewPager2Binding;
import com.wuc.conflict.fragment.RecyclerViewFragment;
import com.wuc.conflict.fragment.RecyclerViewFragment2;
import com.wuc.conflict.viewpager.ViewPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class NestedViewPagerActivity2 extends AppCompatActivity {

  private ActivityNestedViewPager2Binding mBinding;
  final String[] labels = new String[] { "个性推荐", "歌单", "主播电台", "排行榜" };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // setContentView(R.layout.activity_nested_view_pager2);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nested_view_pager2);
    ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this, getPageFragments());
    mBinding.viewpagerView.setAdapter(pagerAdapter);
    new TabLayoutMediator(mBinding.tablayout, mBinding.viewpagerView, new TabLayoutMediator.TabConfigurationStrategy() {
      @Override
      public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        tab.setText(labels[position]);
      }
    }).attach();
    mBinding.tablayoutViewpager.post(new Runnable() {
      @Override
      public void run() {
        // 设置 tablayout_viewpager 的高度和 NestedScrollView 的高度一样
        mBinding.tablayoutViewpager.getLayoutParams().height = mBinding.nestedscrollview.getMeasuredHeight();
        mBinding.tablayoutViewpager.requestLayout();
      }
    });
  }

  private List<Fragment> getPageFragments() {
    List<Fragment> data = new ArrayList<>();
    data.add(new RecyclerViewFragment2());
    data.add(new RecyclerViewFragment2());
    data.add(new RecyclerViewFragment2());
    data.add(new RecyclerViewFragment2());
    return data;
  }
}