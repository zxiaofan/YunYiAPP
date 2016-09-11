// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.hospital;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MedicalNaviDetail$$ViewBinder<T extends com.zxiaofan.yunyi.hospital.MedicalNaviDetail> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624067, "field 'tvHospitalName'");
    target.tvHospitalName = finder.castView(view, 2131624067, "field 'tvHospitalName'");
    view = finder.findRequiredView(source, 2131624153, "field 'tvNaviTitle'");
    target.tvNaviTitle = finder.castView(view, 2131624153, "field 'tvNaviTitle'");
    view = finder.findRequiredView(source, 2131624154, "field 'wv'");
    target.wv = finder.castView(view, 2131624154, "field 'wv'");
  }

  @Override public void unbind(T target) {
    target.tvHospitalName = null;
    target.tvNaviTitle = null;
    target.wv = null;
  }
}
