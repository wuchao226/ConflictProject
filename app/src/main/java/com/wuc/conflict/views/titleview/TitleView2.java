package com.wuc.conflict.views.titleview;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import com.wuc.conflict.R;
import com.wuc.conflict.commonview.BaseCustomView;
import com.wuc.conflict.databinding.TitleView2Binding;
import com.wuc.conflict.databinding.TitleViewBinding;
import java.util.Random;

public class TitleView2 extends BaseCustomView<TitleView2Binding, TitleViewViewModel> {
    public TitleView2(Context context) {
        super(context);
    }

    @Override
    public int setViewLayoutId() {
        return R.layout.title_view2;
    }

    @Override
    public void setDataToView(TitleViewViewModel data) {
        getDataBinding().setViewModel(data);
        int min = 1;
        int max = 4;

        Random r = new Random();
        int randomNumber = r.nextInt(max - min + 1) + min;
        if(randomNumber == 1) {
            getDataBinding().itemFileName.setBackgroundResource(R.drawable.picture_1);
            getDataBinding().itemFileName.getLayoutParams().height = 100;

        } else if(randomNumber == 2) {
            getDataBinding().itemFileName.setBackgroundResource(R.drawable.picture_2);
            getDataBinding().itemFileName.getLayoutParams().height = 200;
        } else if(randomNumber == 3) {
            getDataBinding().itemFileName.setBackgroundResource(R.drawable.picture_3);
            getDataBinding().itemFileName.getLayoutParams().height = 300;
        } else if(randomNumber == 4) {
            getDataBinding().itemFileName.setBackgroundResource(R.drawable.picture_4);
            getDataBinding().itemFileName.getLayoutParams().height = 400;
        }
    }

    @Override
    public void onRootClick(View view) {
        Toast.makeText(getContext(), "clicked...", Toast.LENGTH_SHORT).show();
    }
}
