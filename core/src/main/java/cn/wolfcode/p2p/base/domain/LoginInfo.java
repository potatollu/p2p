package cn.wolfcode.p2p.base.domain;

/**
 * 用户登录对象
 */
public class LoginInfo extends BaseDomain{

    //用户状态:正常
    public static final int STATE_NOMAL = 0;
    //锁定
    public static final int STATE_LOCK = 1;
    //前台用户
    public static final int LOGIN_USER = 0;
    //后台管理员
    public static final int LOGIN_MENAGER = 1;
    //后台审核人
    public static final int LOGIN_AUDIT = 2;

    //用户状态
    private int state=STATE_NOMAL;
    //用户名
    private String username;
    //密码
    private String password;

    //登录用户
    private int userType;

    public Boolean isMgrSiteUser(){
        switch (userType){
            case LOGIN_USER:return false;
            case LOGIN_MENAGER:return true;
            case LOGIN_AUDIT:return true;
        }
        return null;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
