// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GuidThree$$ViewBinder<T extends com.zxiaofan.yunyi.activity.GuidThree> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624135, "field 'welcomeGuideBtn'");
    target.welcomeGuideBtn = finder.castView(view, 2131624135, "field 'welcomeGuideBtn'");
  }

  @Override public void unbind(T target) {
    target.welcomeGuideBtn = null;
  }
}
