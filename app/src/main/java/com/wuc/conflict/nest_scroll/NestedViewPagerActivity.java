package com.wuc.conflict.nest_scroll;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.wuc.conflict.R;
import com.wuc.conflict.databinding.ActivityNestedViewPagerBinding;
import com.wuc.conflict.fragment.RecyclerViewFragment;
import com.wuc.conflict.viewpager.ViewPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class NestedViewPagerActivity extends AppCompatActivity {

  private ActivityNestedViewPagerBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = ActivityNestedViewPagerBinding.inflate(getLayoutInflater());
    // DataBindingUtil.setContentView(this,R.layout.activity_nested_view_pager);
    setContentView(mBinding.getRoot());

    ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this, getPageFragments());
    mBinding.viewpagerView.setAdapter(pagerAdapter);
    // binding.comboContentView.setOffscreenPageLimit(1);
    final String[] labels = new String[] { "linear", "scroll", "recycler" };
    new TabLayoutMediator(mBinding.tablayout, mBinding.viewpagerView, new TabLayoutMediator.TabConfigurationStrategy() {
      @Override
      public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        tab.setText(labels[position]);
      }
    }).attach();
    mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        Log.e("NestedViewPagerActivity", "Refresh started...");
        mBinding.getRoot().postDelayed(new Runnable() {
          @Override
          public void run() {
            mBinding.swipeRefreshLayout.setRefreshing(false);
          }
        }, 1000);
      }
    });
  }

  private List<Fragment> getPageFragments() {
    List<Fragment> data = new ArrayList<>();
    data.add(new RecyclerViewFragment());
    data.add(new RecyclerViewFragment());
    data.add(new RecyclerViewFragment());
    return data;
  }
}