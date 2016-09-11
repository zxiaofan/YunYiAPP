package bean;

/**
 * Describe:就诊人类
 * Created by ${苗}
 * on 2016/3/28.
 */

public class PatientVisits {
    private String gender;//性别
    private int id;
    private String idNo;//身份证号
    private String name;//姓名
    private String phone;//手机号

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
