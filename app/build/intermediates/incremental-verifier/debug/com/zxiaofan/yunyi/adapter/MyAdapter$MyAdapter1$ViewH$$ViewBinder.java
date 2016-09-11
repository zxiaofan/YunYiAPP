// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyAdapter$MyAdapter1$ViewH$$ViewBinder<T extends com.zxiaofan.yunyi.adapter.MyAdapter.MyAdapter1.ViewH> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624365, "field 'haoyuan'");
    target.haoyuan = finder.castView(view, 2131624365, "field 'haoyuan'");
  }

  @Override public void unbind(T target) {
    target.haoyuan = null;
  }
}
