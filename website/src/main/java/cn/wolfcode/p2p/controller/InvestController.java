package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.query.BidRequestQueryObject;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.bussiness.domain.BidRequest;
import cn.wolfcode.p2p.bussiness.service.IBidRequestSevice;
import cn.wolfcode.p2p.util.Constants;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.PageResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 投资相关页面
 */
@Controller
public class InvestController {

    @Autowired
    private IBidRequestSevice bidRequestSevice;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IRealAuthService realAuthService;

    /**
     * 投标
     * @return
     */
    @RequestMapping("borrow_bid")
    @ResponseBody
    public JSONResult borrowBid(Long bidRequestId, BigDecimal amount){
        JSONResult result = new JSONResult();
        try {
            bidRequestSevice.bid(bidRequestId,amount);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }


    @RequestMapping("invest")
    public String invest(){
        return "invest";
    }
    @RequestMapping("invest_list")
    public String investList(@ModelAttribute("qo")BidRequestQueryObject qo, Model model){
        if (qo.getBidRequestState() == -1){
            qo.setBidRequestStates(new int[]{Constants.BIDREQUEST_STATE_BIDDING,
                    Constants.BIDREQUEST_STATE_PAYING_BACK,
                    Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
        }
        PageResult pageResult = bidRequestSevice.queryForList(qo);
        model.addAttribute("pageResult",pageResult);
        return "invest_list";
    }

    @RequestMapping("borrow_info")
    public String borrowInfo(Long id,Model model){
        BidRequest bidRequest = bidRequestSevice.get(id);
        model.addAttribute("bidRequest",bidRequest);
        UserInfo userInfo = userInfoService.get(bidRequest.getCreateUser().getId());
        model.addAttribute("userInfo",userInfo);

        LoginInfo loginInfo = UserContext.getLoginInfo();
        if (loginInfo != null){
            Account account = accountService.get(loginInfo.getId());
            model.addAttribute("account",account);
            if (loginInfo.getId().equals(bidRequest.getCreateUser().getId())){
                model.addAttribute("self",true);
            }
        }
        RealAuth realAuth = realAuthService.get(userInfo.getRealAuthId());
        model.addAttribute("realAuth",realAuth);
        return "borrow_info";
    }
}
