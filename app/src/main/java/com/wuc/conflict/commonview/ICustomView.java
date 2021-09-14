package com.wuc.conflict.commonview;

public interface ICustomView<S extends BaseCustomViewModel> {
    void setData(S data);
}
