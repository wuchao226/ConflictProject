package com.wuc.conflict.nest_scroll;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.wuc.conflict.commonview.BaseCustomViewModel;
import com.wuc.conflict.commonview.ICustomView;

/**
 * @author : wuchao5
 * @date : 2021/9/14 11:21
 * @desciption :
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

  ICustomView view;

  public BaseViewHolder(ICustomView view) {
    super((View) view);
    this.view = view;
  }

  public void bind(@NonNull BaseCustomViewModel item) {
    view.setData(item);
  }
}