package cn.wolfcode.p2p.web.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.query.RealAuthQueryObject;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.util.JSONResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RealAuthController {

    @Autowired
    private IRealAuthService realAuthService;

    @RequestMapping("realAuth")
    public String realAuth(Model model,@ModelAttribute("qo") RealAuthQueryObject qo){
        PageInfo pageResult = realAuthService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "realAuth/list";
    }

    //审核通过/拒绝认证
    @RequestMapping("realAuth_audit")
    @ResponseBody
    public JSONResult realAuthAudit(Long id,int state,String remark){
        JSONResult result = new JSONResult();
        try {
            realAuthService.audict(id,state,remark);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }
}
