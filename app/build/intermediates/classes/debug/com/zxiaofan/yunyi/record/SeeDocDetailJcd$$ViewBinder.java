// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.record;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SeeDocDetailJcd$$ViewBinder<T extends com.zxiaofan.yunyi.record.SeeDocDetailJcd> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624204, "field 'rl'");
    target.rl = finder.castView(view, 2131624204, "field 'rl'");
  }

  @Override public void unbind(T target) {
    target.rl = null;
  }
}
