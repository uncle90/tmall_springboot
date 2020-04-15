package com.finstone.tmall.web;

import com.finstone.tmall.pojo.Category;
import com.finstone.tmall.service.CategoryService;
import com.finstone.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台页面（买家视角）涉及到的 restful 风格接口 api
 */
@RestController
public class ForeRestController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @GetMapping("forehome")
    public Object forehome(){
        //查询所有分类
        List<Category> cs = categoryService.list();

        //查询每个分类下的所有产品
        productService.fillProducts(cs);

        //查询每个分类下的热销商品，并按行分组，一行最多8个。
        productService.fillProductsByRow(cs);

        //去除Product中的Category属性，避免在JSON序列化时无限递归
        //@JsonBackReference

        return cs;
    }




}