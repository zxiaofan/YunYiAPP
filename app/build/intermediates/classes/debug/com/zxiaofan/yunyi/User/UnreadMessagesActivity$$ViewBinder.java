// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.User;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UnreadMessagesActivity$$ViewBinder<T extends com.zxiaofan.yunyi.User.UnreadMessagesActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624039, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131624039, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131624208, "field 'listView6'");
    target.listView6 = finder.castView(view, 2131624208, "field 'listView6'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.listView6 = null;
  }
}
