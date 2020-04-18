package com.finstone.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 1、@Transient
 * @Transient 注解可以加在属性上，也可以加在get()上面。
 * @Transient 注解表示被修饰的属性不会与表字段关联，ORM会自动忽略。
 * 实例类中的属性，如果没有用 @Column 指明关联字段，也没有用 @Transient 明确表示要忽略，
 * 则ORM会将其自动关联到表对应的同名字段——table字段与Entity成员变量无额外映射时（默认不写）。
 */
@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="subTitle")
    private String subTitle;

    @Column(name="originalPrice")
    private Float originalPrice;

    @Column(name="promotePrice")
    private Float promotePrice;

    @Column(name="stock")
    private Integer stock;

    /*首页查询分类及下属产品时，切断递归调用，避免Json序列化时栈溢出。
    @JsonBackReference太彻底，导致部分场景无法使用，改用需要时手动切断递归*/
    //@JsonBackReference
    /*非数据库字段：在属性查询页、添加页、编辑页提供关联信息，包括category.id、category.name等。*/
    @ManyToOne
    @JoinColumn(name="cid")
    private Category category;

    @Column(name="createDate")
    private Date createDate;

    /*非数据库字段：在首页、产品列表页等显示封面图片*/
    @Transient
    private ProductImage firstProductImage;

    /*非数据库字段：单个产品图片集*/
    @Transient
    private List<ProductImage> productSingleImages;

    /*非数据库字段：产品详情图片集*/
    @Transient
    private List<ProductImage> productDetailImages;

    /*非数据库字段：销量*/
    @Transient
    private int saleCount;

    /*非数据库字段：累计评价*/
    @Transient
    private int reviewCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public Float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(Float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductImage getFirstProductImage() {
        return firstProductImage;
    }

    public void setFirstProductImage(ProductImage firstProductImage) {
        this.firstProductImage = firstProductImage;
    }

    public List<ProductImage> getProductSingleImages() {
        return productSingleImages;
    }

    public void setProductSingleImages(List<ProductImage> productSingleImages) {
        this.productSingleImages = productSingleImages;
    }

    public List<ProductImage> getProductDetailImages() {
        return productDetailImages;
    }

    public void setProductDetailImages(List<ProductImage> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }
}
