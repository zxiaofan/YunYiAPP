package util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * 
 * 功能描述: json数据的解析工具类
 * 创建作者: xiaofan
 * 创建时间：
 * 修改描述：
 * 修改作者：
 * 修改时间：
 */
public class JsonUtils {
	
	public JsonUtils() {

	}
	
	/**
	 * 使用JSON工具把数据转换成json对象
	 * @param value 是对解析的集合的类型
	 */
	public static String createJsonString(Object value)throws Exception {
		return JSON.toJSONString(value);
	}

	/**
	 * 对单个javabean进行解析
	 * @param <T>
	 * @param json	要解析的json字符串
	 * @param cls	实体bean的字节码对象
	 * @return		
	 */
	public static <T>T getObject(String json,Class<T> cls)throws Exception {
		return JSON.parseObject(json,cls);
	}
	
	/**
	 * 对list类型进行解析
	 * @param <T>
	 * @param json	要解析的json字符串
	 * @param cls	实体bean的字节码对象
	 * @return
	 */
	public static <T> List<T> getListObject(String json,Class<T> cls)throws Exception {
		return JSON.parseArray(json, cls);
	}
	
	/**
	 * 对MapString类型数据进行解析
	 * @param json	要解析的json字符串
	 * @return
	 */
	public static Map<String, String> getMapStr(String json)throws Exception {
		return JSON.parseObject(json, new TypeReference<Map<String, String>>(){});
	}
	
	/**
	 * 对MapObject类型数据进行解析
	 * @param json	要解析的json字符串
	 * @return
	 */
	public static Map<String, Object> getMapObj(String json)throws Exception {
		return JSON.parseObject(json, new TypeReference<Map<String, Object>>(){});
	}
	
	/**
	 * 对listmap类型进行解析
	 * @param json	要解析的json字符串
	 * @return
	 */
	public static List<Map<String, Object>> getListMap(String json)throws Exception {
		return JSON.parseObject(json,new TypeReference<List<Map<String, Object>>>(){});
	}
}
