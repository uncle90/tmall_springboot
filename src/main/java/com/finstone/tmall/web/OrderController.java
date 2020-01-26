package com.finstone.tmall.web;

import com.finstone.tmall.pojo.Order;
import com.finstone.tmall.pojo.Page4JPA;
import com.finstone.tmall.pojo.ResponseEntity;
import com.finstone.tmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 管理员订单查询
     * @param start
     * @param size
     * @return
     * @throws Exception
     */
    @GetMapping("/orders")
    public Page4JPA<Order> list(
            @RequestParam(name = "strat", defaultValue = "0") int start,
            @RequestParam(value = "size", defaultValue = "" + Page4JPA.defaultSize) int size)
    throws Exception{
        Page4JPA<Order> os = orderService.list(start,size);
        return os;
    }

    /**
     * 订单发货
     * @param id
     * @return
     * @throws Exception
     */
    @PutMapping("orders/delivery/{id}")
    public ResponseEntity delivery(@PathVariable(name = "id") int id) throws Exception{
        ResponseEntity responseEntity = new ResponseEntity();

        Order order = orderService.get(id);
        if(OrderService.waitPay.equals(order.getStatus())){
            responseEntity.setCode(-1);
            responseEntity.setMsg("订单未支付，不能发货！");
            return responseEntity;
        }
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);
        orderService.update(order);
        responseEntity.setCode(0);
        responseEntity.setMsg("已发货！");
        return responseEntity;
    }

}
