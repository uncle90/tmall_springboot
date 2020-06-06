package com.finstone.tmall.web;

import com.finstone.tmall.pojo.*;
import com.finstone.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    ProductImageService productImageService;

    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    ReviewService reviewService;

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

    /**
     * 登录检查
     * @return
     */
    @GetMapping("forecheckLogin")
    public ResponseEntity forecheckLogin(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null){
            return ResponseEntity.fail("请先登录");
        }else{
            return ResponseEntity.success();
        }
    }

    /**
     * 商品详情及评价信息
     * @param pid
     * @return
     * @throws Exception
     */
    @RequestMapping("foreproduct/{pid}")
    public ResponseEntity product(@PathVariable("pid") int pid) throws Exception{
        //产品&图片信息
        Product product = productService.get(pid);
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.SINGLE); //单个（类）图片
        product.setProductSingleImages(pisSingle);
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.DETAIL); //详情（类）图片
        product.setProductDetailImages(pisDetail);

        //商品详情（产品属性）
        List<PropertyValue> pvs = propertyValueService.list(pid);

        //产品评价
        List<Review> reviews = reviewService.list(pid);

        //销量和累计评价
        productService.setSaleCountAndReviewCount(product);

        //封面图片
        productService.setFirstProductImage(product);

        Map<String, Object> map = new HashMap<>();
        map.put("product",product);
        map.put("pvs",pvs);
        map.put("reviews",reviews);
        return ResponseEntity.success(map);
    }

}