package com.finstone.tmall.service;

import com.finstone.tmall.dao.OrderItemDao;
import com.finstone.tmall.pojo.Order;
import com.finstone.tmall.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    OrderItemDao orderItemDao;

    /**
     * 根据订单查询订单项
     * @param order
     * @return
     */
    public List<OrderItem> list(Order order){
        List<OrderItem> orderItems = orderItemDao.findByOrderOrderByIdDesc(order);
        return orderItems;
    }

    /**
     * 向订单中补充订单项等信息
     * @param orders
     */
    public void fill(List<Order> orders){
        for(Order order: orders){
            fill(order);
        }
    }

    public void fill(Order order){
        //订单项
        List<OrderItem> ois = this.list(order);
        order.setOrderItems(ois);

        Integer count = 0; //订单中的商品数量
        float total = 0;  //订单总金额
        if(ois !=null && !ois.isEmpty()){
            for(OrderItem orderItem: ois){
                count += orderItem.getNumber();
                total += orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
            }
        }
        order.setTotalNumber(count);
        order.setTotal(total);
    }

}
