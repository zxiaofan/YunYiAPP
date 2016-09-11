// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.User;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PersonalDetail$$ViewBinder<T extends com.zxiaofan.yunyi.User.PersonalDetail> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624039, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131624039, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131624076, "field 'ed1'");
    target.ed1 = finder.castView(view, 2131624076, "field 'ed1'");
    view = finder.findRequiredView(source, 2131624077, "field 'editText'");
    target.editText = finder.castView(view, 2131624077, "field 'editText'");
    view = finder.findRequiredView(source, 2131624078, "field 'spinner1'");
    target.spinner1 = finder.castView(view, 2131624078, "field 'spinner1'");
    view = finder.findRequiredView(source, 2131624079, "field 'ed2'");
    target.ed2 = finder.castView(view, 2131624079, "field 'ed2'");
    view = finder.findRequiredView(source, 2131624053, "field 'btnSubmit'");
    target.btnSubmit = finder.castView(view, 2131624053, "field 'btnSubmit'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.ed1 = null;
    target.editText = null;
    target.spinner1 = null;
    target.ed2 = null;
    target.btnSubmit = null;
  }
}
