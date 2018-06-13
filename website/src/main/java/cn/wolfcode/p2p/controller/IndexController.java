package cn.wolfcode.p2p.controller;


import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.query.BidRequestQueryObject;
import cn.wolfcode.p2p.base.service.ISmsService;
import cn.wolfcode.p2p.bussiness.domain.IndexTotalData;
import cn.wolfcode.p2p.bussiness.domain.SendSms;
import cn.wolfcode.p2p.bussiness.service.IBidRequestSevice;
import cn.wolfcode.p2p.bussiness.service.IIndexTotalDataService;
import cn.wolfcode.p2p.util.Constants;
import cn.wolfcode.p2p.util.PageResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private IBidRequestSevice bidRequestSevice;
    @Autowired
    private IIndexTotalDataService indexTotalDataService;
    @Autowired
    private ISmsService smsService;

    @RequestMapping("index")
    public String index(Model model, @ModelAttribute("qo")BidRequestQueryObject qo){
        //发表公告
        qo.setBidRequestState(Constants.BIDREQUEST_STATE_PUBLISH_PENDING);
        qo.setPageSize(5);
        PageResult publishPendngBidRequests = bidRequestSevice.queryForList(qo);
        model.addAttribute("publishPendngBidRequests",publishPendngBidRequests);

        //进行中借款
        BidRequestQueryObject tempQo = new BidRequestQueryObject();
        tempQo.setBidRequestStates(new int[]{Constants.BIDREQUEST_STATE_BIDDING,
                Constants.BIDREQUEST_STATE_PAYING_BACK,
                Constants.BIDREQUEST_STATE_COMPLETE_PAY_BACK});
        tempQo.setOrderBy(" br.bidRequestState ");
        tempQo.setPageSize(5);
        PageResult bidRequests = bidRequestSevice.queryForList(tempQo);
        model.addAttribute("bidRequests",bidRequests);
        //投资总人数
        IndexTotalData indexTotalData = indexTotalDataService.account();
        model.addAttribute("indexTotalData",indexTotalData);

        return "main";
    }

    @RequestMapping("message")
    public String message(Model model){
        //smsService.updateState();
        LoginInfo loginInfo = UserContext.getLoginInfo();
        List<SendSms> sendSms = smsService.selectByUserId(loginInfo.getId());
        model.addAttribute("sendSms",sendSms);
        return "message";
    }
}
