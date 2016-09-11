// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.record;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SeeDocDetailDzbl$$ViewBinder<T extends com.zxiaofan.yunyi.record.SeeDocDetailDzbl> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624271, "field 'tvZs'");
    target.tvZs = finder.castView(view, 2131624271, "field 'tvZs'");
    view = finder.findRequiredView(source, 2131624272, "field 'tvXbs'");
    target.tvXbs = finder.castView(view, 2131624272, "field 'tvXbs'");
    view = finder.findRequiredView(source, 2131624273, "field 'tvTgjc'");
    target.tvTgjc = finder.castView(view, 2131624273, "field 'tvTgjc'");
    view = finder.findRequiredView(source, 2131624274, "field 'tvFzjc'");
    target.tvFzjc = finder.castView(view, 2131624274, "field 'tvFzjc'");
    view = finder.findRequiredView(source, 2131624275, "field 'tvZd'");
    target.tvZd = finder.castView(view, 2131624275, "field 'tvZd'");
  }

  @Override public void unbind(T target) {
    target.tvZs = null;
    target.tvXbs = null;
    target.tvTgjc = null;
    target.tvFzjc = null;
    target.tvZd = null;
  }
}
