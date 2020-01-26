package com.finstone.tmall.dao;

import com.finstone.tmall.pojo.Order;
import com.finstone.tmall.pojo.OrderItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {

    //根据订单查询订单项
    List<OrderItem> findByOrderOrderByIdDesc(Order order);

}
