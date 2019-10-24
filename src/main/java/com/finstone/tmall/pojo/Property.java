package com.finstone.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="property")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增主键
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @ManyToOne //此表多条记录对应<外键指向的表>中的一条记录
    @JoinColumn(name="cid")//关联字段，不加会有中间字段
    private Category category;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Property[" + "id=" + id + ", name=" + name + ", category=" + category + "]";
    }
}