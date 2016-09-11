// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.record;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ReportQuery$$ViewBinder<T extends com.zxiaofan.yunyi.record.ReportQuery> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624196, "field 'tvJybg'");
    target.tvJybg = finder.castView(view, 2131624196, "field 'tvJybg'");
    view = finder.findRequiredView(source, 2131624198, "field 'tvJcbg'");
    target.tvJcbg = finder.castView(view, 2131624198, "field 'tvJcbg'");
    view = finder.findRequiredView(source, 2131624195, "field 'lvJybg'");
    target.lvJybg = finder.castView(view, 2131624195, "field 'lvJybg'");
    view = finder.findRequiredView(source, 2131624197, "field 'lvJcbg'");
    target.lvJcbg = finder.castView(view, 2131624197, "field 'lvJcbg'");
  }

  @Override public void unbind(T target) {
    target.tvJybg = null;
    target.tvJcbg = null;
    target.lvJybg = null;
    target.lvJcbg = null;
  }
}
