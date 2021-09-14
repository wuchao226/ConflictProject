package com.wuc.conflict.views.titleview;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import com.wuc.conflict.R;
import com.wuc.conflict.commonview.BaseCustomView;
import com.wuc.conflict.databinding.TitleViewBinding;

public class TitleView extends BaseCustomView<TitleViewBinding, TitleViewViewModel> {
    public TitleView(Context context) {
        super(context);
    }

    @Override
    public int setViewLayoutId() {
        return R.layout.title_view;
    }

    @Override
    public void setDataToView(TitleViewViewModel data) {
        getDataBinding().setViewModel(data);
    }

    @Override
    public void onRootClick(View view) {
        Toast.makeText(getContext(), "clicked...", Toast.LENGTH_SHORT).show();
    }
}
