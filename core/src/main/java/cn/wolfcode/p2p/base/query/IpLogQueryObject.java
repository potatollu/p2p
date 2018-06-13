package cn.wolfcode.p2p.base.query;

import cn.wolfcode.p2p.util.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 登录日志高级查询对象
 */
public class IpLogQueryObject extends QueryObject{
    //用户名
    private String username;
    //登录状态
    private int state=-1;
    //开始时间
    private Date beginDate;
    //结束时间
    private Date endDate;

    //登录用户类型
    private int userType=-1;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public Date getBeginDate() {
        return DateUtil.getBeginDate(beginDate);
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return DateUtil.getEndDate(endDate);
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public String getUsername() {
        return StringUtils.hasLength(username)?username.trim():null;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
