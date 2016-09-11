// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Frag_News$$ViewBinder<T extends com.zxiaofan.yunyi.fragment.Frag_News> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624132, "field 'rg'");
    target.rg = finder.castView(view, 2131624132, "field 'rg'");
    view = finder.findRequiredView(source, 2131624133, "field 'vp'");
    target.vp = finder.castView(view, 2131624133, "field 'vp'");
  }

  @Override public void unbind(T target) {
    target.rg = null;
    target.vp = null;
  }
}
