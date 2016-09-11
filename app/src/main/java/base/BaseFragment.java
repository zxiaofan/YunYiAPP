package base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import util.RecycleUtil;

/**
 * Created by lenovo on 2016/3/16.
 */
public abstract class BaseFragment extends Fragment {
    public View view;
    public OptsharepreInterface o;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getViewId(),null);
        initView();
        initAction();
        return view;
    }

    protected abstract void initView();

    protected abstract void initAction();

    public abstract int getViewId();
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (view != null){
            RecycleUtil.recycleView((ViewGroup) view);
        }
    }
}
