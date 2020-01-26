package com.finstone.tmall.dao;

import com.finstone.tmall.pojo.Order;
import com.finstone.tmall.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Integer> {

    //用户订单视角
    Page<Order> findByUser(User user, Pageable pageable);

}
