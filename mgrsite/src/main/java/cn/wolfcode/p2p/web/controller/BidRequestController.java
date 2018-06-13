package cn.wolfcode.p2p.web.controller;


import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.query.BidRequestQueryObject;
import cn.wolfcode.p2p.bussiness.service.IBidRequestSevice;
import cn.wolfcode.p2p.util.Constants;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class BidRequestController {

    @Autowired
    private IBidRequestSevice bidRequestSevice;


    /**
     * 发标审核
     */
    @RequestMapping("bidrequest_publishaudit")
    @ResponseBody
    public JSONResult bidrequestPublishaudit(Long id, int state, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")Date publishTime,String remark){
        JSONResult result = new JSONResult();
        try {
            bidRequestSevice.publishAudit(id,state,publishTime,remark);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }

    /**
     * 发标审核列表
     * @return
     */
    @RequestMapping("bidrequest_publishaudit_list")
    public String bidrequestPublishauditList(Model model, @ModelAttribute("qo")BidRequestQueryObject qo){
        qo.setBidRequestState(Constants.BIDREQUEST_STATE_PUBLISH_PENDING);
        PageInfo pageResult = bidRequestSevice.queryPage(qo);
        model.addAttribute("pageResult",pageResult);
        return "bidrequest/publish_audit";
    }
    /**
     *满标一审列表
     *
     */
    @RequestMapping("bidrequest_audit1_list")
    public String bidrequestAudit1List(Model model, @ModelAttribute("qo")BidRequestQueryObject qo){
        qo.setBidRequestState(Constants.BIDREQUEST_STATE_APPROVE_PENDING_1);
        PageResult pageResult = bidRequestSevice.queryForList(qo);
        model.addAttribute("pageResult",pageResult);
        return "bidrequest/audit1";
    }

    /**
     * 满标一审
     */
    @RequestMapping("bidrequest_audit1")
    @ResponseBody
    public JSONResult bidrequestAudit1(Long id,int state, String remark){
        JSONResult result = new JSONResult();
        try {
            bidRequestSevice.audit(id,state,remark);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }

    /**
     * 满标二审列表
     * @param model
     * @param qo
     * @return
     */
    @RequestMapping("bidrequest_audit2_list")
    public String bidrequestAudit2List(Model model, @ModelAttribute("qo")BidRequestQueryObject qo){
        qo.setBidRequestState(Constants.BIDREQUEST_STATE_APPROVE_PENDING_2);
        PageResult pageResult = bidRequestSevice.queryForList(qo);
        model.addAttribute("pageResult",pageResult);
        return "bidrequest/audit2";
    }

    /**
     * 满标二审审核
     * @param id
     * @param state
     * @param remark
     * @return
     */
    @RequestMapping("bidrequest_audit2")
    @ResponseBody
    public JSONResult bidrequestAudit2(Long id,int state, String remark){
        JSONResult result = new JSONResult();
        try {
            bidRequestSevice.audit2(id,state,remark);
        }catch (DisplayableException e){
            result.remark(e.getMessage());
        }catch (Exception e){
            result.remark(e.getMessage());
        }
        return result;
    }
}
