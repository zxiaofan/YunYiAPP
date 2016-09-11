// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.registered;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisteredHistory$$ViewBinder<T extends com.zxiaofan.yunyi.registered.RegisteredHistory> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624158, "field 'rlListview'");
    target.rlListview = finder.castView(view, 2131624158, "field 'rlListview'");
    view = finder.findRequiredView(source, 2131624151, "field 'sr'");
    target.sr = finder.castView(view, 2131624151, "field 'sr'");
  }

  @Override public void unbind(T target) {
    target.rlListview = null;
    target.sr = null;
  }
}
