package com.finstone.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String index(){
        return "redirect:home";
    }

    /**
     * 前往首页
     * @return
     */
    @GetMapping(value = "/home")
    public String home(){
        return "fore/home";
    }

    /**
     * 前往登录页
     */
    @GetMapping("/login")
    public String login(){
        return "fore/login";
    }

    /**
     * 前往注册页
     */
    @GetMapping("/register")
    public String register(){
        return "fore/register";
    }

    /**
     * 前往注册成功页
     */
    @GetMapping("/registerSuccess")
    public String registerSuccess(){
        return "fore/registerSuccess";
    }

    /**
     * 退出
     */
    @GetMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:home";
    }

    /**
     * 商品详情页
     * @return
     */
    @GetMapping("product")
    public String product(){
        return "fore/product";
    }

    /**
     * 商品分类页
     * @return
     */
    @GetMapping("category")
    public String category(){
        return "fore/category";
    }

    /**
     * 搜索结果页
     */
    @GetMapping("search")
    public String search(){
        return "fore/searchResult";
    }

    /**
     * 下单页、结算页面
     */
    @GetMapping("buy")
    public String buy(){
        return "fore/buy";
    }

}
