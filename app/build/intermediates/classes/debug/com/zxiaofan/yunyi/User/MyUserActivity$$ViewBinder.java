// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.User;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyUserActivity$$ViewBinder<T extends com.zxiaofan.yunyi.User.MyUserActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624039, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131624039, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131624160, "field 'listView7'");
    target.listView7 = finder.castView(view, 2131624160, "field 'listView7'");
    view = finder.findRequiredView(source, 2131624159, "field 'slistview'");
    target.slistview = finder.castView(view, 2131624159, "field 'slistview'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.listView7 = null;
    target.slistview = null;
  }
}
