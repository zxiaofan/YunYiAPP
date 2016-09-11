// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.registered;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisteredMain$$ViewBinder<T extends com.zxiaofan.yunyi.registered.RegisteredMain> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624172, "field 'tvLoc'");
    target.tvLoc = finder.castView(view, 2131624172, "field 'tvLoc'");
    view = finder.findRequiredView(source, 2131624055, "field 'tvHospital'");
    target.tvHospital = finder.castView(view, 2131624055, "field 'tvHospital'");
    view = finder.findRequiredView(source, 2131624173, "field 'minHospital'");
    target.minHospital = finder.castView(view, 2131624173, "field 'minHospital'");
    view = finder.findRequiredView(source, 2131624177, "field 'tvSjwk'");
    target.tvSjwk = finder.castView(view, 2131624177, "field 'tvSjwk'");
    view = finder.findRequiredView(source, 2131624100, "field 'tvDate'");
    target.tvDate = finder.castView(view, 2131624100, "field 'tvDate'");
    view = finder.findRequiredView(source, 2131624178, "field 'minDate'");
    target.minDate = finder.castView(view, 2131624178, "field 'minDate'");
    view = finder.findRequiredView(source, 2131624182, "field 'tvPtmz'");
    target.tvPtmz = finder.castView(view, 2131624182, "field 'tvPtmz'");
    view = finder.findRequiredView(source, 2131624180, "field 'minPtmz'");
    target.minPtmz = finder.castView(view, 2131624180, "field 'minPtmz'");
  }

  @Override public void unbind(T target) {
    target.tvLoc = null;
    target.tvHospital = null;
    target.minHospital = null;
    target.tvSjwk = null;
    target.tvDate = null;
    target.minDate = null;
    target.tvPtmz = null;
    target.minPtmz = null;
  }
}
