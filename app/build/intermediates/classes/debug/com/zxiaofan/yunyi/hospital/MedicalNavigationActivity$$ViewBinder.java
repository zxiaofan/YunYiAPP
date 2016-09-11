// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.hospital;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MedicalNavigationActivity$$ViewBinder<T extends com.zxiaofan.yunyi.hospital.MedicalNavigationActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624039, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131624039, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131624067, "field 'tvHospitalName'");
    target.tvHospitalName = finder.castView(view, 2131624067, "field 'tvHospitalName'");
    view = finder.findRequiredView(source, 2131624155, "field 'recyclerview'");
    target.recyclerview = finder.castView(view, 2131624155, "field 'recyclerview'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.tvHospitalName = null;
    target.recyclerview = null;
  }
}
