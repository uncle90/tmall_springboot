package com.finstone.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @GetMapping(value="/admin")
    public String admin(){
        return "redirect:admin/category";
    }

    //分类管理
    @GetMapping(value="admin/category")
    public String listCategory(){
        return "admin/listCategory";
    }

    @GetMapping(value="/admin/category/edit")
    public String editCategory(){
        return "admin/editCategory";
    }

    //属性管理
    @GetMapping(value="/admin/property")
    public String listProperty(){
        return "admin/listProperty";
    }

    @GetMapping(value="/admin/property/edit")
    public String editProperty(){
        return "admin/editProperty";
    }

    //产品管理
    @GetMapping(value ="/admin/product")
    public String listProduct(){
        return "admin/listProduct";
    }

    @GetMapping(value ="/admin/product/edit")
    public String editProduct(){
        return "admin/editProduct";
    }

    //产品图片管理
    @GetMapping(value = "/admin/productImage/list")
    public String listProductImage(){
        return "admin/listProductImage";
    }

    //产品属性值管理
    /*
    admin_propertyValue_edit
     */
    @GetMapping(value = "admin_propertyValue_edit")
    public String listPropertyValue(){
        return "admin/editPropertyValue";
    }

    //用户管理
    @GetMapping(value = "admin_user_list")
    public String listUser(){
        return "admin/listUser";
    }

    //订单管理
    @GetMapping(value = "admin_order_list")
    public String listOrder(){
        return "admin/listOrder";
    }

}
