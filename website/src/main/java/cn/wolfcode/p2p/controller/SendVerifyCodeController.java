package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.ISendVerifyCodeService;
import cn.wolfcode.p2p.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SendVerifyCodeController {
    @Autowired
    private ISendVerifyCodeService sendVerifyCodeService;

    @RequestMapping("sendVerifyCode")
    @ResponseBody
    public JSONResult sendVerifyCode(String phoneNumber){
        JSONResult jsonResult = new JSONResult();
        try {
            sendVerifyCodeService.sendVerifyCode(phoneNumber);
        }catch (DisplayableException e){
            jsonResult.remark(e.getMessage());
        }catch (Exception e){
            jsonResult.remark(e.getMessage());
        }
        return jsonResult;
    }

}
