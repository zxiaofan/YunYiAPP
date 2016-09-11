// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.hospital;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ExpertIntroductionActivity$$ViewBinder<T extends com.zxiaofan.yunyi.hospital.ExpertIntroductionActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624039, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131624039, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131624083, "field 'listView4'");
    target.listView4 = finder.castView(view, 2131624083, "field 'listView4'");
    view = finder.findRequiredView(source, 2131624082, "field 'hosname'");
    target.hosname = finder.castView(view, 2131624082, "field 'hosname'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.listView4 = null;
    target.hosname = null;
  }
}
