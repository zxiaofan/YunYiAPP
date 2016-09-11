package util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Description: ${todo}
 * @author: xiaofan
 * @date: ${date} ${time}
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int space;

    /**
     * @param space 传入的值，其单位视为dp
     */
    public SpaceItemDecoration(Context context,int space) {
        this.space = ConvertDpAndPx.Dp2Px(context,space);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if(parent.getChildPosition(view) == 0)
            outRect.top = space;
    }
}
