package bean;

import java.io.Serializable;

/**
 * @Description: ${todo}
 * @author: xiaofan
 * @date: ${date} ${time}
 */
public class CityNewBean implements Serializable {

    public static final String ZONE_CODE="zone_code";
    public static final String ZONE_DESC="zone_desc";
    public static final String ZONE_CODE_PAR="zone_code_par";
    public static final String ZONE_LEVEL="zone_level";

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 1L;

    /**
     * code码
     */
    private String zone_code;

    /**
     * 名称
     */
    private String zone_desc;

    /**
     * 父节点code码
     */
    private String zone_code_par;

    /**
     * 所属等级（1：省，2：市，3：县（区））
     */
    private String zone_level;

    /**
     * 等级（记录选择的等级 0标示省级、1 市级、2县级）
     */
    private int level ;

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    /**
     * 显示数据拼音的首字母

     */
    private String sortLetters;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getZone_code() {
        return zone_code;
    }

    public void setZone_code(String zone_code) {
        this.zone_code = zone_code;
    }

    public String getZone_desc() {
        return zone_desc;
    }

    public void setZone_desc(String zone_desc) {
        this.zone_desc = zone_desc;
    }

    public String getZone_code_par() {
        return zone_code_par;
    }

    public void setZone_code_par(String zone_code_par) {
        this.zone_code_par = zone_code_par;
    }

    public String getZone_level() {
        return zone_level;
    }

    public void setZone_level(String zone_level) {
        this.zone_level = zone_level;
    }
}
