// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.registered;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyOutpatients$$ViewBinder<T extends com.zxiaofan.yunyi.registered.MyOutpatients> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624158, "field 'rlListview'");
    target.rlListview = finder.castView(view, 2131624158, "field 'rlListview'");
    view = finder.findRequiredView(source, 2131624053, "field 'btnSubmit'");
    target.btnSubmit = finder.castView(view, 2131624053, "field 'btnSubmit'");
  }

  @Override public void unbind(T target) {
    target.rlListview = null;
    target.btnSubmit = null;
  }
}
