package cn.wolfcode.p2p.web.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.query.VideoAuthQueryObject;
import cn.wolfcode.p2p.base.service.IVideoAuthService;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.UserContext;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VideoAuthController {
    @Autowired
    private IVideoAuthService videoAuthService;

    /**
     * 审核列表
     * @param model
     * @param qo
     * @return
     */
    @RequestMapping("vedioAuth")
    public String videoAuth(Model model,@ModelAttribute("qo") VideoAuthQueryObject qo){
        qo.setAuditorId(UserContext.getLoginInfo().getId());
        PageInfo pageResult = videoAuthService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "vedioAuth/list";
    }

    /**
     * 提交审核
     */
    @RequestMapping("vedioAuth_audit")
    @ResponseBody
    public JSONResult vedioAuthAudit(Long id,int state,String remark){
        JSONResult result = new JSONResult();
        try {
            videoAuthService.audit(id,state,remark);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }

}
