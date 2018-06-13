package cn.wolfcode.p2p.controller;

import ch.qos.logback.core.util.ContextUtil;
import cn.wolfcode.p2p.base.domain.IpLog;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IIpLogService;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.service.ISmsService;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginInfoController {

    @Autowired
    private ILoginInfoService loginInfoService;
    @Autowired
    private IIpLogService ipLogService;
    @Autowired
    private ISmsService smsService;

    @RequestMapping("hello")
    @ResponseBody
    public String hello(){
        LoginInfo loginInfo = loginInfoService.selectById(1L);
        return "成功"+loginInfo.getUsername();
    }

    @RequestMapping("userLogin")
    @ResponseBody
    public JSONResult login(String username, String password, Model model){
        JSONResult result = new JSONResult();
        //默认登录失败
        int state = IpLog.LOGIN_FAILED;
        int type = IpLog.LOGIN_USER;
        try {
            loginInfoService.login(username,password,false);
            state = IpLog.LOGIN_SUCCESS;
        }catch (DisplayableException e){
            result.remark(e.getMessage());
        }catch (Exception e){
            result.remark(e.getMessage());
        }

        //保存日志
        ipLogService.save(IpLog.LOGIN_USER,username, UserContext.getIp(), state, type);

        return result;
    }
}
