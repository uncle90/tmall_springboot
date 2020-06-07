package com.finstone.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 前台页面（买家视角）跳转
 */
@Controller
public class ForePageController {

    /**
     * 重定向到首页
     * @return
     */
    @GetMapping(value = "/")
    public String home(){
        return "redirect:home";
    }

    /**
     * 前往首页
     * @return
     */
    @GetMapping(value = "home")
    public String forehome(){
        return "fore/home";
    }

    /**
     * 前往登录页
     */
    @GetMapping("loginPage")
    public String loginPage(){
        return "fore/login";
    }

    /**
     * 前往注册页
     */
    @GetMapping("registerPage")
    public String registerPage(){
        return "fore/register";
    }

    /**
     * 前往注册成功页
     */
    @GetMapping("registerSuccess")
    public String registerSuccess(){
        return "fore/registerSuccess";
    }

    /**
     * 退出
     */
    @GetMapping("forelogout")
    public String forelogout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:home";
    }

    /**
     * 商品详情页
     * @return
     */
    @GetMapping("foreproduct")
    public String foreproduct(){
        return "fore/product";
    }

    /**
     * 商品分类页
     * @return
     */
    @GetMapping("forecategory")
    public String forecategory(){
        return "fore/category";
    }

    /**
     * 商品搜索
     */
    @GetMapping("foresearch")
    public String foresearch(){
        return "fore/searchResult";
    }

}
