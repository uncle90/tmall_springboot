package com.finstone.tmall.web;

import com.finstone.tmall.pojo.Category;
import com.finstone.tmall.pojo.ResponseEntity;
import com.finstone.tmall.pojo.User;
import com.finstone.tmall.service.CategoryService;
import com.finstone.tmall.service.ProductService;
import com.finstone.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @Autowired
    UserService userService;

    /**
     * 首页商品信息
     * @return
     */
    @GetMapping("forehome")
    public Object forehome(){
        //查询所有分类
        List<Category> cs = categoryService.list();

        //查询每个分类下的所有产品
        productService.fillProducts(cs);

        //查询每个分类下的热销商品，并按行分组，一行最多8个。
        productService.fillProductsByRow(cs);

        //去除Category.product中的Category属性，避免在JSON序列化时无限递归
        //@JsonBackReference太彻底，导致部分场景无法使用，改用需要时手动切断递归
        categoryService.removeCategoryFromProduct(cs);

        return cs;
    }

    /**
     * 登录
     */
    @PostMapping("forelogin")
    public ResponseEntity forelogin(@RequestBody User user, HttpSession session){
        User userCheck = userService.get(user.getName(), user.getPassword());
        if(userCheck == null){
            return ResponseEntity.fail("账号密码错误");
        }else{
            session.setAttribute("user", user);
            return ResponseEntity.success("登录成功");
        }
    }

    /**
     * 注册
     */
    @PostMapping("register")
    public ResponseEntity foreregister(@RequestBody User user){
        if(userService.isExist(user.getName())){
            return ResponseEntity.fail("用户名已经被使用");
        }
        userService.add(user);
        return ResponseEntity.success("注册成功");
    }

}