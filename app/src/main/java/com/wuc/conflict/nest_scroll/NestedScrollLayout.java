package com.wuc.conflict.nest_scroll;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import com.wuc.conflict.fragment.NestedLogRecyclerView;
import com.wuc.conflict.utils.FlingHelper;

/**
 * @author : wuchao5
 * @date : 2021/9/13 20:11
 * @desciption :
 */
public class NestedScrollLayout extends NestedScrollView {

  private static final String TAG = "NestedScrollLayout";

  private View mTopView;
  private ViewGroup mContentView;

  private FlingHelper mFlingHelper;

  int totalDy = 0;
  /**
   * 用于判断RecyclerView是否在fling
   */
  boolean isStartFling = false;
  /**
   * 记录当前滑动的y轴加速度
   */
  private int velocityY = 0;

  public NestedScrollLayout(@NonNull Context context) {
    super(context);
    init();
  }

  public NestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public NestedScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mFlingHelper = new FlingHelper(getContext());
    setOnScrollChangeListener(new View.OnScrollChangeListener() {
      @Override
      public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        // 如果RecyclerView是在fling
        if (isStartFling) {
          totalDy = 0;
          isStartFling = false;
        }
        if (scrollY == 0) {
          Log.i(TAG, "TOP SCROLL");
          // refreshLayout.setEnabled(true);
        }
        if (scrollY == (getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
          Log.i(TAG, "BOTTOM SCROLL");
          dispatchChildFling();
        }
        // 在 RecyclerView fling 情况下，记录当前 RecyclerView 在y轴的偏移
        totalDy += scrollY - oldScrollY;
      }
    });
  }

  private void dispatchChildFling() {
    if (velocityY != 0) {
      // 根据初始速度得到总距离
      Double splineFlingDistance = mFlingHelper.getSplineFlingDistance(velocityY);
      if (splineFlingDistance > totalDy) {
        // 根据算法得到速度，总距离 - 已经滑动的距离 = 惯性要滑动的距离
        childFling(mFlingHelper.getVelocityByDistance(splineFlingDistance - Double.valueOf(totalDy)));
      }
    }
    totalDy = 0;
    velocityY = 0;
  }

  private void childFling(int velY) {
    RecyclerView childRecyclerView = getChildRecyclerView(mContentView);
    if (childRecyclerView != null) {
      // 惯性滑动
      childRecyclerView.fling(0, velY);
    }
  }

  /**
   * 遍历子view，找到现在在滑动的具体是哪个 instanceof RecyclerView
   */
  private RecyclerView getChildRecyclerView(ViewGroup viewGroup) {
    for (int i = 0; i < viewGroup.getChildCount(); i++) {
      View view = viewGroup.getChildAt(i);
      if (view instanceof RecyclerView && view.getClass() == NestedLogRecyclerView.class) {
        return (RecyclerView) viewGroup.getChildAt(i);
      } else if (viewGroup.getChildAt(i) instanceof ViewGroup) {
        ViewGroup childRecyclerView = getChildRecyclerView((ViewGroup) viewGroup.getChildAt(i));
        if (childRecyclerView instanceof RecyclerView) {
          return (RecyclerView) childRecyclerView;
        }
      }
      continue;
    }
    return null;
  }

  @Override
  public void fling(int velocityY) {
    super.fling(velocityY);
    if (velocityY <= 0) {
      this.velocityY = 0;
    } else {
      isStartFling = true;
      this.velocityY = velocityY;
    }
  }

  @Override
  public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
    super.onNestedPreScroll(target, dx, dy, consumed);
    Log.i(TAG, getScrollY() + "::onNestedPreScroll::" + mTopView.getMeasuredHeight());
    // 向上滑动。若当前topview可见，需要将topview滑动至不可见
    // 向上滚动并且已经滚动的距离小于头部 mTopView 的高度
    boolean hideTop = dy > 0 && getScrollY() < mTopView.getMeasuredHeight();
    if (hideTop) {
      // parent 滚动 dy 距离
      scrollBy(0, dy);
      // 告诉 child parent 消耗了 dy 距离，child 不用消耗了
      consumed[1] = dy;
    }
  }

  /**
   * child每次滚动前，可以先询问parent是否要滚动，即调用dispatchNestedScroll（），
   * 这时可以回调到parent的OnNestedPreScroll（），parent可以在这个回调中先于child滚动
   */
  @Override
  public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
    Log.i("NestedScrollLayout", getScrollY() + "::onNestedPreScroll::" + mTopView.getMeasuredHeight());
    // 向上滑动。若当前topview可见，需要将topview滑动至不可见
    boolean hideTop = dy > 0 && getScrollY() < mTopView.getMeasuredHeight();
    if (hideTop) {
      scrollBy(0, dy);
      consumed[1] = dy;
    }
  }

  /**
   * XML布局被加载完后，就会回调onFinshInfalte这个方法，在这个方法中我们可以初始化控件和数据。
   */
  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    mTopView = ((ViewGroup) getChildAt(0)).getChildAt(0);
    mContentView = (ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(1);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    // 调整contentView的高度为父容器高度，使之填充布局，避免父容器滚动后出现空白
    ViewGroup.LayoutParams lp = mContentView.getLayoutParams();
    lp.height = getMeasuredHeight();
    mContentView.setLayoutParams(lp);
  }
}