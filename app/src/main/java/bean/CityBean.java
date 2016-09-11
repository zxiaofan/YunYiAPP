package bean;

import java.io.Serializable;

public class CityBean  implements Serializable{
	
	public static final String CITY_ID="cityId";
	public static final String PROVINCE_NAME="provinceName";
	public static final String COUNTY_NAME="countyName";
	public static final String CITY_NAME="cityName";
	public static final String COUNTRY_NAME="countryName";
	public static final String COUNTRY_NAME_IN_ENGLISH="countryNameInEnglish";

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	/**
	 * 城市编号
	 */
	private String cityId;
	
	/**
	 * 城市省份
	 */
	private String provinceName;
	
	/**
	 * 城市区域（县）
	 */
	private String countyName;
	
	/**
	 * 城市所在地市(市)
	 */
	private String cityName;
	
	/**
	 * 城市国家
	 */
	private String countryName;
	
	/**
	 * 城市国家（英文）
	 */
	private String countryNameInEnglish;



	/**
	 * 等级（记录选择的等级 0标示省级、1 市级、2县级）
	 */
	private int level ;
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public CityBean() {
		super();
	}

	public CityBean(String provinceName, int excelIndex) {
		super();
		this.provinceName = provinceName;
		this.countryName = "中国";
		this.countryNameInEnglish = "CHINA";
	}

	/**
	 * 城市编号
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * 城市编号
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * 城市省份
	 */

	public String getProvinceName() {
		return provinceName;
	}
	/**
	 * 城市省份
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * 城市区域（市、县）
	 */
	public String getCountyName() {
		return countyName;
	}


	/**
	 * 城市区域（市、县）
	 */
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	/**
	 * 城市所在地市
	 */

	public String getCityName() {
		return cityName;
	}


	/**
	 * 城市所在地市
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * 城市国家
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * 城市国家
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	/**
	 * 城市国家（英文）
	 */
	public String getCountryNameInEnglish() {
		return countryNameInEnglish;
	}

	/**
	 * 城市国家（英文）
	 */
	public void setCountryNameInEnglish(String countryNameInEnglish) {
		this.countryNameInEnglish = countryNameInEnglish;
	}



	@Override
	public String toString() {
		return "CityBean [cityId=" + cityId + ", provinceName=" + provinceName
				+ ", countyName=" + countyName + ", cityName=" + cityName
				+ ", countryName=" + countryName + "]";
	}


}
