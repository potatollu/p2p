package cn.wolfcode.p2p.util;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.vo.VerifyCodeVO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserContext {
    public static final String VERFYCODEVO_IN_SESSION = "VERFYCODEVO_IN_SESSION";
    //将loginInfo放到session中,就是为了在个人中心获取到登录名
    public static final String LOGININFO_IN_SESSION = "logininfo";
    private static HttpSession getRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request.getSession();
    }


    public static void setVerifyCodeVO(VerifyCodeVO vo){
        getRequest().setAttribute(VERFYCODEVO_IN_SESSION,vo);
    }
    public static VerifyCodeVO getVerifyCodeVO(){
        return (VerifyCodeVO) getRequest().getAttribute(VERFYCODEVO_IN_SESSION);
    }


    public static void setLoginInfo(LoginInfo lo){
        getRequest().setAttribute(LOGININFO_IN_SESSION,lo);
    }
    public static LoginInfo getLoginInfo(){
        return (LoginInfo) getRequest().getAttribute(LOGININFO_IN_SESSION);
    }

    public static String getIp() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest().getRemoteAddr();
    }
}
