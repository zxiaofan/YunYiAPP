// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.record;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MedicalExpensesActivity$$ViewBinder<T extends com.zxiaofan.yunyi.record.MedicalExpensesActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624039, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131624039, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131624152, "field 'mrjpLv'");
    target.mrjpLv = finder.castView(view, 2131624152, "field 'mrjpLv'");
    view = finder.findRequiredView(source, 2131624151, "field 'sr'");
    target.sr = finder.castView(view, 2131624151, "field 'sr'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.mrjpLv = null;
    target.sr = null;
  }
}
