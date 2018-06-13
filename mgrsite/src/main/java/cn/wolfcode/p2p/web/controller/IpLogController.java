package cn.wolfcode.p2p.web.controller;

import cn.wolfcode.p2p.base.query.IpLogQueryObject;
import cn.wolfcode.p2p.base.service.IIpLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IpLogController {

    @Autowired
    private IIpLogService ipLogService;

    @RequestMapping("ipLog")
    public String ipLog(Model model, @ModelAttribute("qo")IpLogQueryObject qo){
        PageInfo pageInfo = ipLogService.queryPage(qo);
        model.addAttribute("pageInfo",pageInfo);
        return "ipLog/list";
    }
}
