// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.registered;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DealDetail$$ViewBinder<T extends com.zxiaofan.yunyi.registered.DealDetail> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624054, "field 'appointmentNo'");
    target.appointmentNo = finder.castView(view, 2131624054, "field 'appointmentNo'");
    view = finder.findRequiredView(source, 2131624055, "field 'tvHospital'");
    target.tvHospital = finder.castView(view, 2131624055, "field 'tvHospital'");
    view = finder.findRequiredView(source, 2131624056, "field 'tvDepartment'");
    target.tvDepartment = finder.castView(view, 2131624056, "field 'tvDepartment'");
    view = finder.findRequiredView(source, 2131624057, "field 'tvDocDate'");
    target.tvDocDate = finder.castView(view, 2131624057, "field 'tvDocDate'");
    view = finder.findRequiredView(source, 2131624058, "field 'tvOutpatientType'");
    target.tvOutpatientType = finder.castView(view, 2131624058, "field 'tvOutpatientType'");
    view = finder.findRequiredView(source, 2131624059, "field 'tvPrice'");
    target.tvPrice = finder.castView(view, 2131624059, "field 'tvPrice'");
    view = finder.findRequiredView(source, 2131624060, "field 'tvRegisteredName'");
    target.tvRegisteredName = finder.castView(view, 2131624060, "field 'tvRegisteredName'");
    view = finder.findRequiredView(source, 2131624061, "field 'tvSex'");
    target.tvSex = finder.castView(view, 2131624061, "field 'tvSex'");
    view = finder.findRequiredView(source, 2131624062, "field 'tvPhone'");
    target.tvPhone = finder.castView(view, 2131624062, "field 'tvPhone'");
    view = finder.findRequiredView(source, 2131624063, "field 'tvIdcard'");
    target.tvIdcard = finder.castView(view, 2131624063, "field 'tvIdcard'");
    view = finder.findRequiredView(source, 2131624064, "field 'tvDealDate'");
    target.tvDealDate = finder.castView(view, 2131624064, "field 'tvDealDate'");
  }

  @Override public void unbind(T target) {
    target.appointmentNo = null;
    target.tvHospital = null;
    target.tvDepartment = null;
    target.tvDocDate = null;
    target.tvOutpatientType = null;
    target.tvPrice = null;
    target.tvRegisteredName = null;
    target.tvSex = null;
    target.tvPhone = null;
    target.tvIdcard = null;
    target.tvDealDate = null;
  }
}
