// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.hospital;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PriceSearchActivity$$ViewBinder<T extends com.zxiaofan.yunyi.hospital.PriceSearchActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624039, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131624039, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131624071, "field 'textView36'");
    target.textView36 = finder.castView(view, 2131624071, "field 'textView36'");
    view = finder.findRequiredView(source, 2131624072, "field 'textView37'");
    target.textView37 = finder.castView(view, 2131624072, "field 'textView37'");
    view = finder.findRequiredView(source, 2131624163, "field 'search'");
    target.search = finder.castView(view, 2131624163, "field 'search'");
    view = finder.findRequiredView(source, 2131624164, "field 'search1'");
    target.search1 = finder.castView(view, 2131624164, "field 'search1'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.textView36 = null;
    target.textView37 = null;
    target.search = null;
    target.search1 = null;
  }
}
