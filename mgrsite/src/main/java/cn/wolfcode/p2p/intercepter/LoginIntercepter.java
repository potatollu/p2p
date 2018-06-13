package cn.wolfcode.p2p.intercepter;

import cn.wolfcode.p2p.base.ann.NeedLogin;
import cn.wolfcode.p2p.base.ann.NotNeedLogin;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginIntercepter extends HandlerInterceptorAdapter{
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            NotNeedLogin notneedLogin = method.getMethodAnnotation(NotNeedLogin.class);
            if(notneedLogin == null && UserContext.getLoginInfo() == null){
                response.sendRedirect("/login.html");
                return false;
            }
        }
        return true;
    }
}
