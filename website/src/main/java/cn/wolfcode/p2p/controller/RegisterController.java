package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {

    @Autowired
    private ILoginInfoService loginInfoService;

    @RequestMapping("userRegister")
    @ResponseBody
    public JSONResult regist(String username,String verifycode,String password,String confirmPwd){
        JSONResult result = new JSONResult();
        try {
            loginInfoService.regist(username, verifycode, password, confirmPwd);
        }catch (DisplayableException e){
            result.remark(e.getMessage());
        }catch (Exception e){
            result.remark(e.getMessage());
        }
        return result;
    }

    @RequestMapping("existUsername")
    @ResponseBody
    public boolean existUsername(String username){
        return !loginInfoService.existUsername(username);
    }

}
