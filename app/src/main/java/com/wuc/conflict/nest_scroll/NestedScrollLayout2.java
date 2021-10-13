package com.wuc.conflict.nest_scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class NestedScrollLayout2 extends NestedScrollView {
    public NestedScrollLayout2(@NonNull Context context) {
        super(context);
        init();
    }

    public NestedScrollLayout2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedScrollLayout2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {

    }

    /**
     * child每次滚动前，可以先询问parent是否要滚动，即调用dispatchNestedScroll（），
     * 这时可以回调到parent的OnNestedPreScroll（），parent可以在这个回调中先于child滚动
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        // tablayout_viewpager 的高度和 NestedScrollView 的高度一样，
        // 所以可以用 LinearLayoutCompat的高度 - NestedScrollView的高度，得到 AppCompatImageView
        int headerViewHeight = getChildAt(0).getMeasuredHeight() - getMeasuredHeight();
        if (dy > 0 /* move up*/ && getScrollY() < headerViewHeight/* headerview does not hide completely.*/) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        if (velocityY > 0) {
            ViewPager2 viewPager2 = getChildView(this, ViewPager2.class);
            if(viewPager2 != null) {
                // In this project configuration, ViewPager2 has only one child RecyclerViewImpl,
                // and RecyclerViewImpl has only one FrameLayout, at last,
                // FrameLayout has only one RecyclerView.
                RecyclerView childRecyclerView = getChildView(((ViewGroup)viewPager2.getChildAt(0)), RecyclerView.class);
                if (childRecyclerView != null) {
                    childRecyclerView.fling(0, velocityY);
                }
            }
        }
    }

    private <T> T getChildView(View viewGroup, Class<T> targetClass) {
        if (viewGroup != null && viewGroup.getClass() == targetClass) {
            return (T) viewGroup;
        }

        if(viewGroup instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) viewGroup).getChildCount(); i++) {
                View view = ((ViewGroup) viewGroup).getChildAt(i);
                if (view instanceof ViewGroup) {
                    T result = getChildView(view, targetClass);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }
}
