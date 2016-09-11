// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.record;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PatientInfo$$ViewBinder<T extends com.zxiaofan.yunyi.record.PatientInfo> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624162, "field 'tvName'");
    target.tvName = finder.castView(view, 2131624162, "field 'tvName'");
    view = finder.findRequiredView(source, 2131624061, "field 'tvSex'");
    target.tvSex = finder.castView(view, 2131624061, "field 'tvSex'");
    view = finder.findRequiredView(source, 2131624063, "field 'tvIdcard'");
    target.tvIdcard = finder.castView(view, 2131624063, "field 'tvIdcard'");
    view = finder.findRequiredView(source, 2131624062, "field 'tvPhone'");
    target.tvPhone = finder.castView(view, 2131624062, "field 'tvPhone'");
    view = finder.findRequiredView(source, 2131624053, "field 'btnSubmit'");
    target.btnSubmit = finder.castView(view, 2131624053, "field 'btnSubmit'");
  }

  @Override public void unbind(T target) {
    target.tvName = null;
    target.tvSex = null;
    target.tvIdcard = null;
    target.tvPhone = null;
    target.btnSubmit = null;
  }
}
