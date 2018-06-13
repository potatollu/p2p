package cn.wolfcode.p2p.web.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.query.QueryObject;
import cn.wolfcode.p2p.base.query.RechargeOfflineQueryObject;
import cn.wolfcode.p2p.bussiness.domain.PlatformBankInfo;
import cn.wolfcode.p2p.bussiness.domain.RechargeOffline;
import cn.wolfcode.p2p.bussiness.service.IPlatformBankInfoService;
import cn.wolfcode.p2p.bussiness.service.IRechargeOfflineService;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class RechargeController {
    @Autowired
    private IPlatformBankInfoService bankInfoService;
    @Autowired
    private IRechargeOfflineService offlineService;

    /**
     * 线下充值审核按钮
     */
    @RequestMapping("rechargeOffline_audit")
    @ResponseBody
    public JSONResult rechargeOfflineAudit(Long id,int state,String remark){
        JSONResult result = new JSONResult();
        try {
            offlineService.audit(id,state,remark);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }

    /**
     * 线下充值审核列表
     */
    @RequestMapping("rechargeOffline")
    public String rechargeOffline(Model model, @ModelAttribute("qo") RechargeOfflineQueryObject qo){
        PageResult pageResult = offlineService.query(qo);
        model.addAttribute("pageResult",pageResult);
        List<PlatformBankInfo> banks = bankInfoService.selectAll();
        model.addAttribute("banks",banks);

        return "rechargeoffline/list";
    }

    /**
     * 账户列表
     * @param model
     * @param qo
     * @return
     */
    @RequestMapping("companyBank_list")
    public String companyBankList(Model model, QueryObject qo){
        PageResult pageResult = bankInfoService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "platformbankinfo/list";
    }

    /**
     * 保存账户信息
     * @param bankInfo
     * @return
     */
    @RequestMapping("companyBank_update")
    @ResponseBody
    public JSONResult companyBankUpdate(PlatformBankInfo bankInfo){
        JSONResult result = new JSONResult();
        try {
            bankInfoService.saveOrUpdaet(bankInfo);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }
}

