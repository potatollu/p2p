package cn.wolfcode.p2p.base.domain;

import com.alibaba.fastjson.JSON;
import javafx.scene.input.DataFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证对象
 */
public class RealAuth extends BaseAuditDomain{
    //性别
    public static final int SEX_MAN = 0;
    public static final int SEX_WOMEN = 1;

    //用户填写的

    private String realName;//真实姓名
    private int sex;//性别
    private String idNumber;//身份证号
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bornDate;//生日
    private String address;//地址
    private String image1;//图片路径1
    private String image2;//图片路径2


    public String getSexDisplay(){
        return sex==RealAuth.SEX_MAN ? "霸道总裁":"黄花大闺女";
    }

    public String getJsonString(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        map.put("state",state);
        map.put("username",applier.getUsername());
        map.put("realName",realName);
        map.put("idNumber",idNumber);
        map.put("sex",getSexDisplay());
        map.put("bornDate", DateFormat.getDateInstance().format(bornDate));
        map.put("address",address);
        map.put("image1",image1);
        map.put("image2",image2);
        return JSON.toJSONString(map);
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }
}
