package com.finstone.tmall.web;

import com.finstone.tmall.pojo.Category;
import com.finstone.tmall.pojo.Page4JPA;
import com.finstone.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /*@GetMapping(value="/categories")
    public List<Category> list() throws Exception{
        return categoryService.list();
    }*/

    @GetMapping(value="/categories")
    public Page4JPA<Category> list(
            @RequestParam(name="start", defaultValue = "0") int start, //name和value参数等价，互为别名
            @RequestParam(name="size", defaultValue = "" + Page4JPA.defaultSize) int size)
            throws Exception {
        return categoryService.list(start, size);
    }
}
