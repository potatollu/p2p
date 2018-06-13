package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.OrderTime;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.domain.VideoAuth;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.ILoginInfoService;
import cn.wolfcode.p2p.base.service.IOrdertimeService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.base.service.IVideoAuthService;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.MD5;
import cn.wolfcode.p2p.util.UserContext;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 视屏认证
 */
@Controller
public class VideoAuthController {

    @Autowired
    private ILoginInfoService loginInfoService;
    @Autowired
    private IOrdertimeService ordertimeService;
    @Autowired
    private IVideoAuthService videoAuthService;
    @Autowired
    private IUserInfoService userInfoService;
    /**
     * 视屏预约
     * @return
     */
    @RequestMapping("videoAuditOrder")
    public String videoAuditOrder(Model model){

        //如果已有预约,查询预约对象
        LoginInfo loginInfo = UserContext.getLoginInfo();
        UserInfo userInfo = userInfoService.get(loginInfo.getId());
        //用户已经完成视频认证
        if(userInfo.hasVedioAuth()){
            model.addAttribute("success",true);
            return "videoOrder";
        }
        VideoAuth tempVideoAuth = videoAuthService.getByStateAndUserId(VideoAuth.STATE_NOMAL, loginInfo.getId());
        if (tempVideoAuth != null){
            model.addAttribute("videoOrder",tempVideoAuth);
            return "videoOrder";
        }

        //预约客服
        List<LoginInfo> auditors = loginInfoService.listByUserType(LoginInfo.LOGIN_AUDIT);
        model.addAttribute("auditors",auditors);
        //预约日期
        List<Date> orderDates = new ArrayList<Date>();
        Date now = new Date();
        orderDates.add(now);
        orderDates.add(DateUtils.addDays(now,1));
        orderDates.add(DateUtils.addDays(now,2));
        orderDates.add(DateUtils.addDays(now,3));
        model.addAttribute("orderDates",orderDates);
        //预约时间,从表里去查
        List<OrderTime> orderTimes = ordertimeService.selectAll();
        model.addAttribute("orderTimes",orderTimes);
        return "videoOrder";
    }

    //预约时间
    @RequestMapping("apply")
    @ResponseBody
    public JSONResult apply(Long auditorId,String orderDate,Long timeId){
        JSONResult result = new JSONResult();
        try {
            videoAuthService.apply(auditorId,orderDate,timeId);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }

}
