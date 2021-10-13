package com.wuc.conflict.views.xxrecyclerview;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.wuc.conflict.nest_scroll.BaseViewHolder;
import com.wuc.conflict.views.titleview.TitleView2;
import com.wuc.conflict.views.titleview.TitleViewViewModel;
import java.util.List;

public class RecyclerAdapter2 extends RecyclerView.Adapter<BaseViewHolder> {
  public List<String> data;

  public RecyclerAdapter2(List<String> data) {
    this.data = data;
  }

  @NonNull
  @Override
  public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new BaseViewHolder(new TitleView2(parent.getContext()));
  }

  @Override
  public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
    holder.bind(new TitleViewViewModel(data.get(position)));
  }

  @Override
  public int getItemCount() {
    return data.size();
  }
}

