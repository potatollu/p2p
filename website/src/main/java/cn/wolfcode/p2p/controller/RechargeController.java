package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.bussiness.domain.PlatformBankInfo;
import cn.wolfcode.p2p.bussiness.domain.RechargeOffline;
import cn.wolfcode.p2p.bussiness.service.IPlatformBankInfoService;
import cn.wolfcode.p2p.bussiness.service.IRechargeOfflineService;
import cn.wolfcode.p2p.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RechargeController {

    @Autowired
    private IPlatformBankInfoService platformBankInfoService;
    @Autowired
    private IRechargeOfflineService rechargeOfflineService;

    @RequestMapping("recharge")
    public String realAuth(Model model){
        List<PlatformBankInfo> banks = platformBankInfoService.selectAll();
        model.addAttribute("banks",banks);
        return "recharge";
    }

    @RequestMapping("recharge_save")
    @ResponseBody
    public JSONResult rechargeSave(RechargeOffline rechargeOffline){
        JSONResult result = new JSONResult();
        try {
            rechargeOfflineService.apply(rechargeOffline);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }
}
