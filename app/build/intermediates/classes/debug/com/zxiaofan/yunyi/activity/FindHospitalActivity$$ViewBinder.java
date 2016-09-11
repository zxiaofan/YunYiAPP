// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FindHospitalActivity$$ViewBinder<T extends com.zxiaofan.yunyi.activity.FindHospitalActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624087, "field 'finddoc'");
    target.finddoc = finder.castView(view, 2131624087, "field 'finddoc'");
    view = finder.findRequiredView(source, 2131624092, "field 'listView'");
    target.listView = finder.castView(view, 2131624092, "field 'listView'");
    view = finder.findRequiredView(source, 2131624039, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131624039, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131624089, "field 'tv1'");
    target.tv1 = finder.castView(view, 2131624089, "field 'tv1'");
    view = finder.findRequiredView(source, 2131624090, "field 'tv2'");
    target.tv2 = finder.castView(view, 2131624090, "field 'tv2'");
    view = finder.findRequiredView(source, 2131624091, "field 'tv3'");
    target.tv3 = finder.castView(view, 2131624091, "field 'tv3'");
    view = finder.findRequiredView(source, 2131624088, "field 'l1'");
    target.l1 = finder.castView(view, 2131624088, "field 'l1'");
    view = finder.findRequiredView(source, 2131624065, "field 'refresh'");
    target.refresh = finder.castView(view, 2131624065, "field 'refresh'");
  }

  @Override public void unbind(T target) {
    target.finddoc = null;
    target.listView = null;
    target.titleBar = null;
    target.tv1 = null;
    target.tv2 = null;
    target.tv3 = null;
    target.l1 = null;
    target.refresh = null;
  }
}
