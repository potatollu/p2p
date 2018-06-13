package cn.wolfcode.p2p.web.controller;

import cn.wolfcode.p2p.base.ann.NotNeedLogin;
import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IIpLogService;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.service.impl.IpLogServiceImpl;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginCotroller {

    @Autowired
    private ILoginInfoService loginInfoService;
    @Autowired
    private IIpLogService iIpLogService;

    @RequestMapping("userLogin")
    @ResponseBody
    @NotNeedLogin
    public JSONResult login(String username, String password){
        JSONResult result = new JSONResult();
        //默认登录失败
        int state = IpLog.LOGIN_FAILED;
        int userType = IpLog.LOGIN_MENAGER;
        try {
            loginInfoService.login(username,password,true);
            state = IpLog.LOGIN_SUCCESS;
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }

        //保存日志
        iIpLogService.save(IpLog.LOGIN_USER,username, UserContext.getIp(),state,userType);

        return result;
    }
}
