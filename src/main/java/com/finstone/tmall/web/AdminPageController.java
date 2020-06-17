package com.finstone.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    //后天管理入口
    @GetMapping(value="")
    public String admin(){
        return "redirect:/admin/categories";
    }

    @GetMapping(value="/")
    public String admin_(){
        return "redirect:/admin/categories";
    }

    //分类管理
    @GetMapping(value="/categories")
    public String listCategory(){
        return "admin/listCategory";
    }

    @GetMapping(value="/categories/edit")
    public String editCategory(){
        return "admin/editCategory";
    }

    //属性管理
    @GetMapping(value="/properties")
    public String listProperty(){
        return "admin/listProperty";
    }

    @GetMapping(value="/properties/edit")
    public String editProperty(){
        return "admin/editProperty";
    }

    //产品管理
    @GetMapping(value ="/products")
    public String listProduct(){
        return "admin/listProduct";
    }

    @GetMapping(value ="/products/edit")
    public String editProduct(){
        return "admin/editProduct";
    }

    //产品图片管理
    @GetMapping(value = "/productImages")
    public String listProductImage(){
        return "admin/listProductImage";
    }

    //产品属性值管理
    @GetMapping(value = "/propertyValues/edit")
    public String listPropertyValue(){
        return "admin/editPropertyValue";
    }

    //用户管理
    @GetMapping(value = "/users")
    public String listUser(){
        return "admin/listUser";
    }

    //订单管理
    @GetMapping(value = "/orders")
    public String listOrder(){
        return "admin/listOrder";
    }

}
