// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.registered;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisteredDetail$$ViewBinder<T extends com.zxiaofan.yunyi.registered.RegisteredDetail> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
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
    view = finder.findRequiredView(source, 2131624162, "field 'tvName'");
    target.tvName = finder.castView(view, 2131624162, "field 'tvName'");
    view = finder.findRequiredView(source, 2131624061, "field 'tvSex'");
    target.tvSex = finder.castView(view, 2131624061, "field 'tvSex'");
    view = finder.findRequiredView(source, 2131624062, "field 'tvPhone'");
    target.tvPhone = finder.castView(view, 2131624062, "field 'tvPhone'");
    view = finder.findRequiredView(source, 2131624063, "field 'tvIdcard'");
    target.tvIdcard = finder.castView(view, 2131624063, "field 'tvIdcard'");
    view = finder.findRequiredView(source, 2131624169, "field 'ivHead'");
    target.ivHead = finder.castView(view, 2131624169, "field 'ivHead'");
    view = finder.findRequiredView(source, 2131624053, "field 'btnSubmit'");
    target.btnSubmit = finder.castView(view, 2131624053, "field 'btnSubmit'");
    view = finder.findRequiredView(source, 2131624168, "field 'llChangePatient'");
    target.llChangePatient = finder.castView(view, 2131624168, "field 'llChangePatient'");
    view = finder.findRequiredView(source, 2131624167, "field 'llMain'");
    target.llMain = finder.castView(view, 2131624167, "field 'llMain'");
  }

  @Override public void unbind(T target) {
    target.tvHospital = null;
    target.tvDepartment = null;
    target.tvDocDate = null;
    target.tvOutpatientType = null;
    target.tvPrice = null;
    target.tvName = null;
    target.tvSex = null;
    target.tvPhone = null;
    target.tvIdcard = null;
    target.ivHead = null;
    target.btnSubmit = null;
    target.llChangePatient = null;
    target.llMain = null;
  }
}
