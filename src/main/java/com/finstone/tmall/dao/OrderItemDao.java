package com.finstone.tmall.dao;

import com.finstone.tmall.pojo.Order;
import com.finstone.tmall.pojo.OrderItem;
import com.finstone.tmall.pojo.Product;
import com.finstone.tmall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {

    //根据订单查询订单项
    List<OrderItem> findByOrderOrderByIdDesc(Order order);

    //根据商品查询订单项
    List<OrderItem> findByProduct(Product product);

    //查询用户购物车列表（未提交的订单项）
    List<OrderItem> findByUserAndOrderIsNull(User user);

}
