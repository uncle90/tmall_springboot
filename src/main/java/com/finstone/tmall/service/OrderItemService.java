package com.finstone.tmall.service;

import com.finstone.tmall.dao.OrderItemDao;
import com.finstone.tmall.pojo.Order;
import com.finstone.tmall.pojo.OrderItem;
import com.finstone.tmall.pojo.Product;
import com.finstone.tmall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    public OrderItem get(Integer id){
        return orderItemDao.findOne(id);
    }

    public OrderItem add(OrderItem orderItem){
        return orderItemDao.save(orderItem);
    }

    public void delete(Integer id){
        orderItemDao.delete(id);
    }

    public OrderItem update(OrderItem orderItem){
        return orderItemDao.save(orderItem);
    }

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
     * 查询用户购物车清单
     * @param user
     * @return
     */
    public List<OrderItem> listCartByUser(User user){
        List<OrderItem> orderItems = orderItemDao.findByUserAndOrderIsNull(user);
        return orderItems;
    }

    /**
     * 添加到购物车，合并同类商品。
     * @param product
     * @param user
     * @param num
     * @return
     */
    public OrderItem addCart(Product product, User user, Integer num){
        OrderItem orderItem = null;
        //检查购物车是否有同类商品
        boolean exist = false;
        List<OrderItem> ois = this.listCartByUser(user);
        if(ois!=null && !ois.isEmpty()){
            for(OrderItem oi: ois){
                if(oi.getProduct().getId() == product.getId()){
                    orderItem = oi;
                    exist = true;
                    break;
                }
            }
        }
        //修改订单项
        if(exist){
            orderItem.setNumber(orderItem.getNumber()+num);
            this.update(orderItem); //追加商品数量
        }else{
            orderItem = new OrderItem();
            orderItem.setNumber(num);
            orderItem.setProduct(product);
            orderItem.setUser(user);
            this.add(orderItem);    //新建订单项
        }
        return orderItem;
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

    /**
     * 合计指定产品的销量
     * @param product
     * @return
     */
    public int getSaleCount(Product product){
        int saleCount = 0;
        List<OrderItem> ois = orderItemDao.findByProduct(product);
        for(OrderItem orderItem: ois){
            saleCount += orderItem.getNumber();
        }
        return saleCount;
    }

}
