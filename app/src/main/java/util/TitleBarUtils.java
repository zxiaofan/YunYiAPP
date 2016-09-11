package util;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxiaofan.yunyi.R;

/**
 * 
 * @ClassName: TitleBarUtils
 * @Description:统一封装的标题栏方法
 * @author: xiaofan
 * @date: 2015-12-16 上午9:51:07
 * 
 */
public class TitleBarUtils extends RelativeLayout {
	// 左右边图片按钮
	private ImageView iv_left, iv_right_one, iv_right_two;
	// 标题，右边文字按钮
	private TextView tv_title, tv_right;
	public ImageView getIv_right_one() {
		return iv_right_one;
	}

	public ImageView getIv_right_two() {
		return iv_right_two;
	}


	// 整个布局，左边返回按钮布局
	private RelativeLayout rl, rl_left;
	// 标题
	private String title;
	// 左右边图片按钮Drawable
	private Drawable leftImage, rightImageTwo, rightImageOne;
	// 标题颜色
	private int titleTextColor;
	// 标题字体大小
	private float titleTextSize;
	// 右侧如果是textview不是imageview
	private String rightText;
	// 右侧文字颜色（默认）
	private int rightTextColor;
	// 右侧字体大小（默认）
	private float rightTextSize;

	public TitleBarUtils(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		View view = LayoutInflater.from(context).inflate(R.layout.custom_title,
				this, true);
		TypedArray arr = context.obtainStyledAttributes(attrs,
				R.styleable.TitleBarLayout);
		title = arr.getString(R.styleable.TitleBarLayout_titleText);
		titleTextColor = arr.getColor(
				R.styleable.TitleBarLayout_titleTextColor, Color.WHITE);
		titleTextSize = arr.getDimension(
				R.styleable.TitleBarLayout_titleTextSize, 20.0f);
		leftImage = arr.getDrawable(R.styleable.TitleBarLayout_leftImage);
		leftImage = context.getResources().getDrawable(R.drawable.left_arrow);
		rightImageOne = arr
				.getDrawable(R.styleable.TitleBarLayout_rightImageOne);
		rightImageTwo = arr
				.getDrawable(R.styleable.TitleBarLayout_rightImageTwo);
		rightText = arr.getString(R.styleable.TitleBarLayout_rightText);
		rightTextColor = arr.getColor(
				R.styleable.TitleBarLayout_rightTextColor, Color.WHITE);
		rightTextSize = arr.getDimension(
				R.styleable.TitleBarLayout_rightTextSize, 18.0f);
		arr.recycle();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		iv_left = (ImageView) findViewById(R.id.iv_left);
		iv_right_one = (ImageView) findViewById(R.id.iv_right_one);
		iv_right_two = (ImageView) findViewById(R.id.iv_right_two);
		tv_title = (TextView) findViewById(R.id.tv_title);
		rl = (RelativeLayout) findViewById(R.id.rl);
		rl_left = (RelativeLayout) findViewById(R.id.rl_left);
		tv_right = (TextView) findViewById(R.id.tv_right);

		if (!TextUtils.isEmpty(title)) {
			setTitle(title);
		} else {
			tv_title.setVisibility(View.GONE);
		}

		if (!TextUtils.isEmpty(rightText)) {
			setTitle(title);
		} else {
			tv_right.setVisibility(View.GONE);
		}

		if (leftImage != null) {
			iv_left.setBackground(leftImage);
		} else {
			iv_left.setVisibility(View.GONE);
		}

		if (rightImageOne != null) {
			iv_right_one.setBackground(rightImageOne);
		} else {
			iv_right_one.setVisibility(View.GONE);
		}

		if (rightImageTwo != null) {
			iv_right_two.setBackground(rightImageTwo);
		} else {
			iv_right_two.setVisibility(View.GONE);
		}
		setTitleTextColor(titleTextColor);
		setTitleTextSize(titleTextSize);
		setRightTextColor(rightTextColor);
		setRightTextSize(rightTextSize);
	}

	/**
	 * 
	 * @Title: setLeftImage   
	 * @date 2015-12-16 上午9:55:47
	 * @Description: 设置标题左边图片   
	 * @param: @param leftImageResId      
	 * @return: void      
	 * @throws
	 */
	@SuppressLint("NewApi")
	public void setLeftImage(int leftImageResId) {
		iv_left.setBackgroundResource(leftImageResId);
		iv_left.setVisibility(View.VISIBLE);
	}

	/**
	 * 
	 * @Title: setTitle   
	 * @date 2015-12-16 上午9:55:59
	 * @Description: 设置标题文字  
	 * @param: @param title      
	 * @return: void      
	 * @throws
	 */
	public void setTitle(String title) {
		tv_title.setText(title);
		tv_title.setVisibility(View.VISIBLE);
	}

	/**
	 * 
	 * @Title: setTitleTextColor   
	 * @date 2015-12-16 上午9:56:20
	 * @Description: 设置标题字体颜色   
	 * @param: @param titleTextColor      
	 * @return: void      
	 * @throws
	 */
	public void setTitleTextColor(int titleTextColor) {
		tv_title.setTextColor(titleTextColor);
	}

	/**
	 * 
	 * @Title: setTitleTextSize   
	 * @date 2015-12-16 上午9:56:34
	 * @Description: 设置标题字体大小  
	 * @param: @param titleTextSize      
	 * @return: void      
	 * @throws
	 */
	public void setTitleTextSize(float titleTextSize) {
		tv_title.setTextSize(titleTextSize);
	}

	/**
	 * 
	 * @Title: setRightText   
	 * @date 2015-12-16 上午9:56:48
	 * @Description: 设置右侧文字   
	 * @param: @param rightText      
	 * @return: void      
	 * @throws
	 */
	public void setRightText(String rightText) {
		tv_right.setText(rightText);
		tv_right.setVisibility(View.VISIBLE);
	}

	/**
	 * 
	 * @Title: setRightTextColor   
	 * @date 2015-12-16 上午9:57:24
	 * @Description:设置右侧文字颜色   
	 * @param: @param rightTextColor      
	 * @return: void      
	 * @throws
	 */
	public void setRightTextColor(int rightTextColor) {
		tv_right.setTextColor(rightTextColor);
	}

	/**
	 * 
	 * @Title: setRightTextSize   
	 * @date 2015-12-16 上午9:57:42
	 * @Description: 设置右侧文字大小 
	 * @param: @param rightTextSize      
	 * @return: void      
	 * @throws
	 */
	public void setRightTextSize(float rightTextSize) {
		tv_right.setTextSize(rightTextSize);
	}

	/**
	 * 
	 * @Title: setRightImageTwo   
	 * @date 2015-12-16 上午9:57:58
	 * @Description: 设置标题右边第二个图片  
	 * @param: @param rightOneImageResId      
	 * @return: void      
	 * @throws
	 */
	@SuppressLint("NewApi")
	public void setRightImageTwo(int rightOneImageResId) {
		iv_right_two.setBackgroundResource(rightOneImageResId);
		iv_right_two.setVisibility(View.VISIBLE);
	}

	/**
	 * 
	 * @Title: setRightImageOne   
	 * @date 2015-12-16 上午9:58:06
	 * @Description:设置标题右边第一个图片   
	 * @param: @param rightTwoImageResId      
	 * @return: void      
	 * @throws
	 */
	@SuppressLint("NewApi")
	public void setRightImageOne(int rightTwoImageResId) {
		iv_right_one.setBackgroundResource(rightTwoImageResId);
		iv_right_one.setVisibility(View.VISIBLE);
	}

	/**
	 * 
	 * @Title: setLeftButtonClick   
	 * @date 2015-12-16 上午9:58:25
	 * @Description: 设置标题栏左边按钮监听事件   
	 * @param: @param onClickListener      
	 * @return: void      
	 * @throws
	 */
	public void setLeftButtonClick(OnClickListener onClickListener) {
		rl_left.setOnClickListener(onClickListener);
	}

	/**
	 * 
	 * @Title: setRightButtonOneClick   
	 * @date 2015-12-16 上午9:58:36
	 * @Description:设置标题栏右边第一个按钮监听事件   
	 * @param: @param onClickListener      
	 * @return: void      
	 * @throws
	 */
	public void setRightButtonOneClick(OnClickListener onClickListener) {
		iv_right_one.setOnClickListener(onClickListener);
	}

	/**
	 * 
	 * @Title: setRightButtonTwoClick   
	 * @date 2015-12-16 上午9:58:46
	 * @Description: 设置标题栏右边第二个按钮监听事件   
	 * @param: @param onClickListener      
	 * @return: void      
	 * @throws
	 */
	public void setRightButtonTwoClick(OnClickListener onClickListener) {
		iv_right_two.setOnClickListener(onClickListener);
	}

	/**
	 * 
	 * @Title: setRightTextClick   
	 * @date 2015-12-16 上午9:59:03
	 * @Description: 设置标题栏右边textview按钮监听事件  
	 * @param: @param onClickListener      
	 * @return: void      
	 * @throws
	 */
	public void setRightTextClick(OnClickListener onClickListener) {
		tv_right.setOnClickListener(onClickListener);
	}
}
