package bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.zxiaofan.yunyi.R;

import db.XZQHDataHelper;

public class DataAddDataBase {

	public static void setProvinceData(Context context) {
			XZQHDataHelper dbHelper = new XZQHDataHelper(context);
			/**
			 * 通过context对象获取到项目raw文件夹下面的文件流
			 */
			InputStream input = context.getResources().openRawResource(
					R.raw.mydatastring);
			BufferedReader reader = null;
			Reader in = null;
			try {
				in = new InputStreamReader(input);
				reader = new BufferedReader(in);
				String tempString = null;
				StringBuffer sb = new StringBuffer();
				/**
				 * 把文件中的数据读出来存到StringBuffer中
				 */
				while ((tempString = reader.readLine()) != null) {
					sb.append(tempString);
				}
				String data = sb.toString();
				/**
				 * 将数据转成字符串后进行拆分处理(通过特殊字符@将所有数据拆成一条条的省市县数据
				 * --格式为：2044&中国&安徽&宣城&广德&CHINA)
				 */
				String[] province = data.split("@");
				List<CityBean> dataList = new ArrayList<CityBean>();
				for (String string : province) {
					/**
					 * 将每条数据拆分成cityBean对象的属性
					 */
					String[] item = string.split("&");
					CityBean cityBean = new CityBean();
					for (int i = 0; i < item.length; i++) {
						switch (i) {
						case 0:
							cityBean.setCityId(item[i]);
							break;
						case 1:
							cityBean.setCountryName(item[i]);
							break;
						case 2:
							cityBean.setProvinceName(item[i]);
							break;
						case 3:
							cityBean.setCityName(item[i]);
							break;
						case 4:
							cityBean.setCountyName(item[i]);
							break;
						case 5:
							cityBean.setCountryNameInEnglish(item[i]);
							break;
						default:
							break;
						}
					}
					dataList.add(cityBean);
				}
				/**
				 * 通过数据库帮助类把数据存到数据库中
				 */
				dbHelper.addCityData(dataList);
				Log.wtf("嗒嗒", dbHelper.queryAllCity().size() + "");
				reader.close();
				in.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
