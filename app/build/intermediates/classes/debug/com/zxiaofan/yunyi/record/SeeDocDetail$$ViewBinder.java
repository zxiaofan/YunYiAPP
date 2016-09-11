// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.record;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SeeDocDetail$$ViewBinder<T extends com.zxiaofan.yunyi.record.SeeDocDetail> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624296, "field 'tvContent'");
    target.tvContent = finder.castView(view, 2131624296, "field 'tvContent'");
    view = finder.findRequiredView(source, 2131624479, "field 'tvFyd'");
    target.tvFyd = finder.castView(view, 2131624479, "field 'tvFyd'");
    view = finder.findRequiredView(source, 2131624480, "field 'tvCfd'");
    target.tvCfd = finder.castView(view, 2131624480, "field 'tvCfd'");
    view = finder.findRequiredView(source, 2131624481, "field 'tvJyd'");
    target.tvJyd = finder.castView(view, 2131624481, "field 'tvJyd'");
    view = finder.findRequiredView(source, 2131624482, "field 'tvJcd'");
    target.tvJcd = finder.castView(view, 2131624482, "field 'tvJcd'");
    view = finder.findRequiredView(source, 2131624100, "field 'tvDate'");
    target.tvDate = finder.castView(view, 2131624100, "field 'tvDate'");
    view = finder.findRequiredView(source, 2131624200, "field 'tvDzbl'");
    target.tvDzbl = finder.castView(view, 2131624200, "field 'tvDzbl'");
    view = finder.findRequiredView(source, 2131624201, "field 'tvFjyd'");
    target.tvFjyd = finder.castView(view, 2131624201, "field 'tvFjyd'");
    view = finder.findRequiredView(source, 2131624202, "field 'tvFjcd'");
    target.tvFjcd = finder.castView(view, 2131624202, "field 'tvFjcd'");
    view = finder.findRequiredView(source, 2131624203, "field 'tvFcfd'");
    target.tvFcfd = finder.castView(view, 2131624203, "field 'tvFcfd'");
    view = finder.findRequiredView(source, 2131624199, "field 'llFragment'");
    target.llFragment = finder.castView(view, 2131624199, "field 'llFragment'");
  }

  @Override public void unbind(T target) {
    target.tvContent = null;
    target.tvFyd = null;
    target.tvCfd = null;
    target.tvJyd = null;
    target.tvJcd = null;
    target.tvDate = null;
    target.tvDzbl = null;
    target.tvFjyd = null;
    target.tvFjcd = null;
    target.tvFcfd = null;
    target.llFragment = null;
  }
}
