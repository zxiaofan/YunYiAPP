// Generated code from Butter Knife. Do not modify!
package com.zxiaofan.yunyi.User;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AddUserActivity$$ViewBinder<T extends com.zxiaofan.yunyi.User.AddUserActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624039, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131624039, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131624045, "field 'et1'");
    target.et1 = finder.castView(view, 2131624045, "field 'et1'");
    view = finder.findRequiredView(source, 2131624046, "field 'et2'");
    target.et2 = finder.castView(view, 2131624046, "field 'et2'");
    view = finder.findRequiredView(source, 2131624047, "field 'et3'");
    target.et3 = finder.castView(view, 2131624047, "field 'et3'");
    view = finder.findRequiredView(source, 2131624048, "field 'et4'");
    target.et4 = finder.castView(view, 2131624048, "field 'et4'");
    view = finder.findRequiredView(source, 2131624049, "field 'button4'");
    target.button4 = finder.castView(view, 2131624049, "field 'button4'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.et1 = null;
    target.et2 = null;
    target.et3 = null;
    target.et4 = null;
    target.button4 = null;
  }
}
