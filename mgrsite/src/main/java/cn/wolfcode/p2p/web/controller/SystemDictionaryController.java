package cn.wolfcode.p2p.web.controller;


import cn.wolfcode.p2p.base.domain.SystemDictionary;
import cn.wolfcode.p2p.base.domain.SystemDictionaryItem;
import cn.wolfcode.p2p.base.exception.DisplayableException;
import cn.wolfcode.p2p.base.query.SystemDictionaryQueryObject;
import cn.wolfcode.p2p.base.service.ISystemDictionaryService;
import cn.wolfcode.p2p.util.JSONResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SystemDictionaryController {

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequestMapping("systemDictionary_list")
    public String systemDictionary(Model model, @ModelAttribute("qo")SystemDictionaryQueryObject qo){
        PageInfo pageInfo = systemDictionaryService.queryPage(qo);
        model.addAttribute("pageResult",pageInfo);
        return "systemdic/systemDictionary_list";
    }

    @RequestMapping("systemDictionary_update")
    @ResponseBody
    public JSONResult systemDictionaryUpdate(SystemDictionary dir){
        JSONResult result = new JSONResult();
        try {
            systemDictionaryService.saveOrUpdate(dir);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }

    @RequestMapping("systemDictionaryItem_list")
    public String systemDictionaryItem(Model model, @ModelAttribute("qo")SystemDictionaryQueryObject qo){
        PageInfo pageInfo = systemDictionaryService.queryItem(qo);
        model.addAttribute("pageResult",pageInfo);
        //数据字典分类
        List<SystemDictionary> systemDictionaryGroups = systemDictionaryService.queryAllDir();
        model.addAttribute("systemDictionaryGroups",systemDictionaryGroups);
        return "systemdic/systemDictionaryItem_list";
    }

    @RequestMapping("systemDictionaryItem_update")
    @ResponseBody
    public JSONResult systemDictionaryItemUpdate(SystemDictionaryItem item){
        JSONResult result = new JSONResult();
        try {
            systemDictionaryService.saveOrUpdateItem(item);
        }catch (DisplayableException e){
           result.remark(e.getMessage());
        }catch (Exception e){
           result.remark(e.getMessage());
        }
        return result;
    }


}
