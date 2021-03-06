package com.finstone.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finstone.tmall.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="order_")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String orderCode;
    private String address;
    private String post;
    private String receiver;
    private String mobile;
    private String userMessage;

    /**
     * 订单创建时间
     * @JsonFormat     后台时间 ==> 前后时间
     * @DateTimeFormat 前台时间 ==> 后台时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date confirmDate;

    /**
     * 订单状态（编码）
     */
    private String status;

    @ManyToOne
    @JoinColumn(name = "uid")
    @JsonIgnoreProperties({"id","anonymousName"})
    private User user;

    /**
     * 商品数量
     */
    @Transient
    private int totalNumber;

    /**
     * 订单总金额
     */
    @Transient
    private double total;

    /**
     * 订单项
     */
    @Transient
    private List<OrderItem> orderItems;

    /**
     * 订单状态（名称）
     */
    @Transient
    private String statusDesc;

    /**
     * 非数据库字段：staus中文含义
     * @return
     */
    public String getStatusDesc() {
        if(statusDesc!=null){
            return statusDesc;
        }
        String desc = "未知";
        switch (status){
            case OrderService.waitPay:
                desc="待付款";
                break;
            case OrderService.waitDelivery:
                desc="待发货";
                break;
            case OrderService.waitConfirm:
                desc="待收货";
                break;
            case OrderService.waitReview:
                desc="等评价";
                break;
            case OrderService.finish:
                desc="完成";
                break;
            case OrderService.delete:
                desc="刪除";
                break;
            default:
                desc="未知";
        }
        return desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}