// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DrugPriceActivity$$ViewBinder<T extends com.zxiaofan.yunyi.activity.DrugPriceActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624039, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131624039, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131624071, "field 'textView36'");
    target.textView36 = finder.castView(view, 2131624071, "field 'textView36'");
    view = finder.findRequiredView(source, 2131624072, "field 'textView37'");
    target.textView37 = finder.castView(view, 2131624072, "field 'textView37'");
    view = finder.findRequiredView(source, 2131624075, "field 'listView2'");
    target.listView2 = finder.castView(view, 2131624075, "field 'listView2'");
    view = finder.findRequiredView(source, 2131624073, "field 'searchEtInput'");
    target.searchEtInput = finder.castView(view, 2131624073, "field 'searchEtInput'");
    view = finder.findRequiredView(source, 2131624074, "field 'searchIn'");
    target.searchIn = finder.castView(view, 2131624074, "field 'searchIn'");
    view = finder.findRequiredView(source, 2131624065, "field 'refresh'");
    target.refresh = finder.castView(view, 2131624065, "field 'refresh'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.textView36 = null;
    target.textView37 = null;
    target.listView2 = null;
    target.searchEtInput = null;
    target.searchIn = null;
    target.refresh = null;
  }
}
