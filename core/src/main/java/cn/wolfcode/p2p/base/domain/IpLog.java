package cn.wolfcode.p2p.base.domain;

import java.util.Date;

/**
 * 日志登录记录
 */
public class IpLog extends BaseDomain{
    //登录状态:正常
    public static final int LOGIN_SUCCESS = 0;
    //锁定
    public static final int LOGIN_FAILED = 1;
    //前台用户
    public static final int LOGIN_USER = 0;
    //后台管理员
    public static final int LOGIN_MENAGER = 1;
    private int state;
    private String ip;
    private String username;
    private Date loginTime;
    private int userType;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }


    public String getStateDisplay(){
        return state==LOGIN_SUCCESS ? "登陆成功" : "登录失败";
    }

    public String getUserTypeDisplay(){
        return userType==LOGIN_USER?"前台用户":"后台管理员";
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
