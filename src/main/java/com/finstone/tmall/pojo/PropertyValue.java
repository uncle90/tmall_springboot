package com.finstone.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="propertyvalue")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class PropertyValue {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //private Integer pid;  //产品id, Product.id,  一产品对多属性值
    @ManyToOne
    @JoinColumn(name="pid")
    private Product product;

    //private Integer ptid; //属性id, Property.id, 一属性对多属性值
    @ManyToOne
    @JoinColumn(name="ptid")
    private Property property;

    private String value; //属性值，与Property.name对应。

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}