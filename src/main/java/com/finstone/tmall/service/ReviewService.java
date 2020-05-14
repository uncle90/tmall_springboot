package com.finstone.tmall.service;

import com.finstone.tmall.dao.ReviewDao;
import com.finstone.tmall.pojo.Order;
import com.finstone.tmall.pojo.Product;
import com.finstone.tmall.pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewDao reviewDao;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    /**
     * 添加商品评价，更改订单状态
     * @param review
     * @param oid
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = {"Exception"})
    public void add(Review review, int oid){
        reviewDao.save(review);
        //更改订单状态
        Order order = orderService.get(oid);
        order.setStatus(OrderService.finish);
        orderService.update(order);
    }

    public void delete(int id){
        reviewDao.delete(id);
    }

    public void update(Review review){
        reviewDao.save(review);
    }

    public Review get(int id){
        return reviewDao.findOne(id);
    }

    /**
     * 查询指定产品的所有评论
     * @param pid
     * @return
     */
    public List<Review> list(int pid){
        Product product = productService.get(pid);
        List<Review> reviews = reviewDao.findByProductOrderByIdDesc(product);
        return reviews;
    }

    /**
     * 合计指定产品的评论数
     * @param product
     * @return
     */
    public int getCount(Product product){
        return reviewDao.countByProduct(product);
    }

    /**
     * 设置评论用户
     * @param reviews
     */
    public void setUser(List<Review> reviews){
        for(Review review: reviews){
            this.setUser(review);
        }
    }

    public void setUser(Review review){}

}