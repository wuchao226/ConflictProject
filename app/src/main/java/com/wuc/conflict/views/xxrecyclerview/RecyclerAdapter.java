package com.wuc.conflict.views.xxrecyclerview;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wuc.conflict.nest_scroll.BaseViewHolder;
import com.wuc.conflict.views.titleview.TitleView;
import com.wuc.conflict.views.titleview.TitleViewViewModel;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
  public List<String> data;

  public RecyclerAdapter(List<String> data) {
    this.data = data;
  }

  @NonNull
  @Override
  public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new BaseViewHolder(new TitleView(parent.getContext()));
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

