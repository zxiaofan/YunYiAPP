package view;


import android.content.Context;
import android.util.AttributeSet;  
import android.view.GestureDetector;  
import android.view.GestureDetector.SimpleOnGestureListener;  
import android.view.MotionEvent;  
import android.widget.ScrollView;  

public class VerticalScrollView extends ScrollView {  

	private GestureDetector mGestureDetector;  

	public VerticalScrollView(Context context, AttributeSet attrs) {  
		super(context, attrs);  
		mGestureDetector = new GestureDetector(context, new YScrollDetector());  
	}  

	@Override  
	public boolean onInterceptTouchEvent(MotionEvent ev) {  
		return super.onInterceptTouchEvent(ev)  
				&& mGestureDetector.onTouchEvent(ev);  
	}  

	class YScrollDetector extends SimpleOnGestureListener {  

		@Override  
		public boolean onScroll(MotionEvent e1, MotionEvent e2,  
				float distanceX, float distanceY) {  
			//������ǹ������ӽ�ˮƽ����,����false,������ͼ�������� 
			return (Math.abs(distanceY) > Math.abs(distanceX));  
		}  
	}  
}