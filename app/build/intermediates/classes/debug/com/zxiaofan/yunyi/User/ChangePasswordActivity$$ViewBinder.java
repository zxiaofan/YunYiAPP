// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.User;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ChangePasswordActivity$$ViewBinder<T extends com.zxiaofan.yunyi.User.ChangePasswordActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624039, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131624039, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131624050, "field 'etAccount'");
    target.etAccount = finder.castView(view, 2131624050, "field 'etAccount'");
    view = finder.findRequiredView(source, 2131624051, "field 'etPwd'");
    target.etPwd = finder.castView(view, 2131624051, "field 'etPwd'");
    view = finder.findRequiredView(source, 2131624052, "field 'etPwd1'");
    target.etPwd1 = finder.castView(view, 2131624052, "field 'etPwd1'");
    view = finder.findRequiredView(source, 2131624053, "field 'btnSubmit'");
    target.btnSubmit = finder.castView(view, 2131624053, "field 'btnSubmit'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.etAccount = null;
    target.etPwd = null;
    target.etPwd1 = null;
    target.btnSubmit = null;
  }
}
