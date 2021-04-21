package com.finstone.tmall.web;

import cn.hutool.json.JSONObject;
import com.finstone.tmall.pojo.*;
import com.finstone.tmall.service.*;
import com.finstone.tmall.util.DateSyncUtil;
import com.finstone.tmall.util.ProductComparator;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

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

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    /**
     * 首页商品信息
     * @return
     */
    @GetMapping("forehome")
    @ApiOperation("首页商品信息查询")
    public Object home(){
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
    public ResponseEntity login(@RequestBody User user, HttpSession session){
        User userCheck = userService.get(user.getName(), user.getPassword());
        if(userCheck == null){
            return ResponseEntity.fail("账号密码错误");
        }else{
            //主键为空表示"未持久化"，findByUser会报错——"TransientObjectException: object references an unsaved transient instance"
            user.setId(userCheck.getId());
            session.setAttribute("user", user);
            return ResponseEntity.success("登录成功");
        }
    }

    /**
     * 注册
     */
    @PostMapping("foreregister")
    public ResponseEntity register(@RequestBody User user){
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
    public ResponseEntity checkLogin(HttpSession session){
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
    @GetMapping("foreproduct/{pid}")
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

    /**
     * 前台分类页
     * @param cid
     * @param sort 排序方式(可选)
     * @return
     * @throws Exception
     */
    @GetMapping("forecategory/{cid}")
    public ResponseEntity category(@PathVariable("cid") Integer cid, String sort) throws Exception{

        Category category = categoryService.get(cid); //当前分类
        productService.fillProducts(category);        //填充分类下的产品
        productService.setSaleCountAndReviewCount(category.getProducts()); //产品销量&评价数量
        //去除Category.product中的Category属性，避免在JSON序列化时无限递归
        //@JsonBackReference太彻底，导致部分场景无法使用，改用需要时手动切断递归
        categoryService.removeCategoryFromProduct(category);
        //产品排序:
        if(sort!=null){
            //方法一：实现Comparator接口
            Collections.sort(category.getProducts(), new ProductComparator(sort));

            /*
            //方法二：在内部类中定义比较器
            switch (sort){
                case "all": //热门（销量*评论数）
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o1.getSaleCount()*o1.getReviewCount() - o2.getSaleCount()*o2.getReviewCount();
                        }
                    });
                    break;
                case "review": //评价数多
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o1.getReviewCount() - o2.getReviewCount();
                        }
                    });
                    break;
                case "date": //发布日期近
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o1.getCreateDate().compareTo(o2.getCreateDate());
                        }
                    });
                    break;
                case "saleCount": //销量多
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return -(o1.getSaleCount() - o2.getSaleCount());
                        }
                    });
                    break;
                case "price": //价格低
                    Collections.sort(category.getProducts(), new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o1.getPromotePrice().compareTo(o2.getPromotePrice());
                        }
                    });
                    break;
            }*/
        }

        Map<String, Object> map = new HashMap<>();
        map.put("category",category);
        return ResponseEntity.success(map);
    }

    /**
     * 商品搜索
     * @param keyword
     * @return
     */
    @PostMapping("foresearch")
    public ResponseEntity search(String keyword){
        if(keyword==null){
            keyword = "";
        }
        List<Product> ps= productService.search(keyword, 0, 8);
        //销量和累计评价
        productService.setSaleCountAndReviewCount(ps);
        //封面图片
        productService.setFirstProductImage(ps);

        Map<String, Object> map = new HashMap<>();
        map.put("ps",ps);
        return ResponseEntity.success(map);
    }

    /**
     * 立即购买某种商品。如果购物车有同类商品，则追加数量。
     * @param jsonbody
     * @param session
     * @return
     */
    @PostMapping("forebuyone")
    public ResponseEntity buyone(@RequestBody String jsonbody, HttpSession session
            /*@RequestParam("pid") Integer pid, @RequestParam("num") Integer num*/){

        User user = (User) session.getAttribute("user");
        JSONObject jsonObject = new JSONObject(jsonbody);
        Integer pid = Integer.parseInt(jsonObject.get("pid").toString());
        Integer num = Integer.parseInt(jsonObject.get("num").toString());

        Product product= productService.get(pid);
        OrderItem orderItem = orderItemService.addCart(product, user, num);

        Map<String,Object> map = new HashMap<>();
        map.put("oiid",orderItem.getId());
        return ResponseEntity.success(map);
    }

    /**
     * 结算页面。查看订单详情，列出订单项，计算总价，并把订单项ois放在session中。
     * @param session
     * @param oiid
     * @return
     */
    @GetMapping("forebuy")
    public ResponseEntity buy(HttpSession session, @RequestParam("oiid") String[] oiid){
        List<OrderItem> ois = new ArrayList<>();
        double total = 0;
        for(String strid: oiid){
            int id = Integer.parseInt(strid);
            OrderItem orderItem = orderItemService.get(id);
            //封面图片
            productService.setFirstProductImage(orderItem.getProduct());
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();//总价
            ois.add(orderItem);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("ois",ois);
        session.setAttribute("ois",ois);//把订单项放入回话，给其他页面使用
        return ResponseEntity.success(map);
    }

    /**
     * 提交订单。价格不需要额外保存，在订单项中。
     * @param session - 用户, 订单项
     * @param order 接收订单参数
     * @return
     */
    @PostMapping("forecreateOrder")
    public ResponseEntity createOrder(HttpSession session, Order order){
        //用户&订单项
        User user = (User) session.getAttribute("user");
        List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");
        //订单字段
        Date date = new Date();
        order.setOrderCode(DateSyncUtil.format(date)+ RandomUtils.nextInt(10000));//时间戳
        order.setCreateDate(date);
        order.setUser(user);
        order.setStatus(OrderService.waitPay);
        //生成订单，返回订单总价、编号。
        double total = orderService.add(order, ois);
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("oid",order.getId());
        return ResponseEntity.success(map);
    }

}