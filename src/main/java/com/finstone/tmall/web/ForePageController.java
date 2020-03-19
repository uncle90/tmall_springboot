package com.finstone.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前台页面（买家视角）跳转
 */
@Controller
public class ForePageController {

    /**
     * 首页
     * @return
     */
    @GetMapping(value = "/")
    public String home(){
        return "redirect:home";
    }
    @GetMapping(value = "home")
    public String forehome(){
        return "fore/home";
    }


}
