package cn.wolfcode.p2p.controller;

import cn.wolfcode.p2p.base.domain.RealAuth;
import cn.wolfcode.p2p.base.domain.UserInfo;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.service.IRealAuthService;
import cn.wolfcode.p2p.base.service.IUserInfoService;
import cn.wolfcode.p2p.util.JSONResult;
import cn.wolfcode.p2p.util.UploadUtil;
import cn.wolfcode.p2p.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class RealAuthController {

    @Autowired
    private IRealAuthService realAuthService;
    @Autowired
    private IUserInfoService userInfoService;

    @RequestMapping("realAuth")
    public String realAuth(Model model){
        //判断是否完成实名认证->调到成功页面
        Long userId = UserContext.getLoginInfo().getId();
        UserInfo userInfo = userInfoService.get(userId);
        if(userInfo.isRealAuth()){
            RealAuth realAuth = realAuthService.get(userInfo.getRealAuthId());
            model.addAttribute("realAuth",realAuth);
            return "realAuth_result";
        }
        //判断是否完成实名认证->调到等待页面
        if (userInfo.getRealAuthId()!=null){
            model.addAttribute("auditing",true);
            return "realAuth_result";
        }
        //跳到填写资料页面
        return "realAuth";
    }


    @RequestMapping("realAuth_save")
    @ResponseBody
    public JSONResult realAuthSave(RealAuth realAuth){
        JSONResult result = new JSONResult();
        try {
            //实名认证申请
            realAuthService.apply(realAuth);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }

    @Value("${uploadpath}")
    private String uploadpath;

    //图片上传
    @RequestMapping("uploadPhoto")
    @ResponseBody
    public String uploadPhoto(MultipartFile file){
        return UploadUtil.upload(file,uploadpath);
    }

}
