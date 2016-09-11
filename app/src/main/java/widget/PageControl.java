package widget;


import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

/**
 * 分页圆点类
 * @author lifei
 *
 */
public class PageControl {

	private LinearLayout layout;
	private TextView[] textViews;
	private TextView textView;
	private int pageSize;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	private int selectedImage = R.mipmap.radio_sel;
	private int unselectedImage = R.mipmap.radio;

	private int currentPage = 0;
	private Context mContext;

	public PageControl(Context context, LinearLayout layout, int pageSize) {
		this.mContext = context;
		this.pageSize = pageSize;
		this.layout = layout;

		initDots();
	}

	void initDots() {
		textViews = new TextView[pageSize];
		for (int i = 0; i < pageSize; i++) {
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(30, 30);
			params.setMargins(8, 8, 8, 0);
			textView = new TextView(mContext);
			textView.setLayoutParams(params);
//			textView.setLayoutParams(new LinearLayout.LayoutParams(30, 30));
//			textView.setPadding(8, 8, 8, 8);

			textViews[i] = textView;
			if (i == 0) {
				textViews[i].setBackgroundResource(R.mipmap.index_main_1);
			} else {
				textViews[i].setBackgroundResource(R.mipmap.index_main_2);
			}
			layout.addView(textViews[i]);
		}
	}

	boolean isFirst() {
		return this.currentPage == 0;
	}

	boolean isLast() {
		return this.currentPage == pageSize;
	}

	public void selectPage(int current) {
		for (int i = 0; i < textViews.length; i++) {
			textViews[current].setBackgroundResource(R.mipmap.index_main_1);
			if (current != i) {
				textViews[i].setBackgroundResource(R.mipmap.index_main_2);
			}
		}
	}

	void turnToNextPage() {
		if (!isLast()) {
			currentPage++;
			selectPage(currentPage);
		}
	}

	void turnToPrePage() {
		if (!isFirst()) {
			currentPage--;
			selectPage(currentPage);
		}
	}
}
