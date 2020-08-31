package com.finstone.tmall.service;

import com.finstone.tmall.dao.OrderDao;
import com.finstone.tmall.pojo.Order;
import com.finstone.tmall.pojo.OrderItem;
import com.finstone.tmall.pojo.Page4JPA;
import com.finstone.tmall.pojo.Product;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    //订单状态常量
    public static final String waitPay = "waitPay";            //待支付
    public static final String waitDelivery = "waitDelivery"; //待发货
    public static final String waitConfirm = "waitConfirm";   //待确认收货
    public static final String waitReview = "waitReview";     //待评价
    public static final String finish = "finish";              //订单结束
    public static final String delete = "delete";              //订单删除

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderItemService orderItemService;

    public Order get(Integer id){
        return orderDao.findOne(id);
    }

    public Order add(Order order) {
        return orderDao.save(order);
    }

    /**
     * 创建订单,更新订单项,返回订单总价.遇到任何异常Exception都回滚.
     * @param order
     * @param ois
     * @return 总价
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = {"Exception"})
    public double add(Order order, List<OrderItem> ois){
        double total = 0;
        //创建订单
        this.add(order);
        //更新订单项 oid
        for(OrderItem oi: ois){
            oi.setOrder(order);
            orderItemService.update(oi);
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
        }
        return total;
    }

    public void delete(Integer id){
        orderDao.delete(id);
    }

    public Order update(Order order){
        return orderDao.save(order);
    }


    /**
     * 订单及订单项查询
     * @param strat
     * @param size
     * @return
     */
    public Page4JPA<Order> list(int strat, int size){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(strat, size, sort);
        Page page = orderDao.findAll(pageable);
        Page4JPA<Order> os = new Page4JPA<Order>(page);
        //向订单中补充订单项等信息
        orderItemService.fill(os.getContent());
        return os;
    }

}
