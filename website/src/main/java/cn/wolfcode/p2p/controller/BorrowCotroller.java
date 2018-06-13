package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.query.PaymentScheduleQueryObject;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.bussiness.domain.BidRequest;
import cn.wolfcode.p2p.bussiness.service.IBidRequestSevice;
import cn.wolfcode.p2p.bussiness.service.IPaymentScheduleService;
import cn.wolfcode.p2p.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BorrowCotroller {

    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IBidRequestSevice bidRequestSevice;
    @Autowired
    private IPaymentScheduleService paymentScheduleService;

    /**
     * 还款
     */
    @RequestMapping("returnMoney")
    @ResponseBody
    public JSONResult returnMone(Long id){
        JSONResult result = new JSONResult();
        try {
            bidRequestSevice.returnMone(id);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }

    /**
     * 还款列表
     */
    @RequestMapping("borrowBidReturn_list")
    public String borrowBidReturnList(Model model, @ModelAttribute("qo")PaymentScheduleQueryObject qo){
        PageResult pageResult = paymentScheduleService.query(qo);
        model.addAttribute("pageResult",pageResult);
        Account account = accountService.get(UserContext.getLoginInfo().getId());
        model.addAttribute("account",account);
        return "returnmoney_list";
    }
    /**
     * 借款申请
     */
    @RequestMapping("borrow_apply")
    @ResponseBody
    public JSONResult borrowApply(BidRequest br){
        JSONResult result = new JSONResult();
        try {
            bidRequestSevice.apply(br);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }

    /**
     * 借款申请页面
     * @param model
     * @return
     */
    @RequestMapping("borrowInfo")
    public String boorowApply(Model model){
        //最小借款金额
        model.addAttribute("minBidRequestAmount", Constants.BORROW_MIN_AMOUNT);
        //最大借款金额
        Account account = accountService.selectById(UserContext.getLoginInfo().getId());
        model.addAttribute("account",account);
        //最小投标金额
        model.addAttribute("minBidAmount",Constants.BID_MIN_AMOUNT);
        //最小利润
        model.addAttribute("minBidRequestRate",Constants.BORROW_MIN_RATE);
        //最大利润
        model.addAttribute("maxBidRequestRate",Constants.BORROW_MAX_RATE);
        return  "borrow_apply";
    }

    @RequestMapping("borrow")
    public String borrow(Model model){
        //如果没有登录就跳转到借款首页静态页面
        LoginInfo loginInfo = UserContext.getLoginInfo();
        if (loginInfo == null){
            return "redirect:/borrowPage.html";
        }
        model.addAttribute("account",accountService.selectById(loginInfo.getId()));
        model.addAttribute("userinfo",userInfoService.get(loginInfo.getId()));

        return "borrow";
    }
}
