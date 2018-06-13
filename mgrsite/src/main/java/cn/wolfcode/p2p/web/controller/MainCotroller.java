package cn.wolfcode.p2p.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainCotroller {


    @RequestMapping("main")
    public String login(){
        return "main";
    }
}
