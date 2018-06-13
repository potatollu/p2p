package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.ann.NeedLogin;
import cn.wolfcode.p2p.base.domain.Account;
import cn.wolfcode.p2p.base.domain.LoginInfo;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IAccountService;
import cn.wolfcode.p2p.base.service.ISystemDictionaryService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PersonalController {

    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private ISystemDictionaryService systemDictionaryService;
    @NeedLogin
    @RequestMapping("personal")
    public String personal(Model model) {
        Account account = accountService.selectById(UserContext.getLoginInfo().getId());
        model.addAttribute("account",account);
        return "personal";
    }

    //填写基本资料
    //@NeedLogin
    @RequestMapping("basicInfo")
    public String basicInfo(Model model){
        LoginInfo loginInfo = UserContext.getLoginInfo();
        model.addAttribute("userinfo",userInfoService.get(loginInfo.getId()));
        List<SystemDictionaryItem> educationBackgrounds = systemDictionaryService.listItemBySn("educationBackground");
        List<SystemDictionaryItem> incomeGrades = systemDictionaryService.listItemBySn("incomeGrade");
        List<SystemDictionaryItem> marriages = systemDictionaryService.listItemBySn("marriage");
        List<SystemDictionaryItem> kidCounts = systemDictionaryService.listItemBySn("kidCount");
        List<SystemDictionaryItem> houseConditions = systemDictionaryService.listItemBySn("houseCondition");

        model.addAttribute("educationBackgrounds",educationBackgrounds);
        model.addAttribute("incomeGrades",incomeGrades);
        model.addAttribute("marriages",marriages);
        model.addAttribute("kidCounts",kidCounts);
        model.addAttribute("houseConditions",houseConditions);

        return "userInfo";
    }

    //保存基本资料
    @RequestMapping("basicInfo_save")
    @ResponseBody
    public JSONResult basicInfoSave(UserInfo userInfo){
        JSONResult result = new JSONResult();
        try {
            userInfoService.basicInfoSave(userInfo);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }
}
