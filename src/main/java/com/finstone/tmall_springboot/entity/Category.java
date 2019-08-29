package com.finstone.tmall_springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * 参考：
 * JPA相关注解：https://www.oracle.com/technetwork/cn/middleware/ias/toplink-jpa-annotations-100895-zhs.html
 * https://blog.csdn.net/dandandeshangni/article/details/79497804
 * https://www.cnblogs.com/sandea/p/8252809.html
 */
//

@Entity //这是一个实体类
@Table(name="category") //对应的表名为 category
//用 jpa 来做实体类的持久化，jpa 默认会使用 hibernate, 在 jpa 工作过程中，就会创造代理类来继承 Category ，
//并添加 handler 和 hibernateLazyInitializer 这两个无须 json 化的属性
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"}) //自定义类的to json过程——忽略不需要的内容，调整（日期）变量的格式。
public class Category {


    @Id //声明此属性为主键。该属性值可以通过应该自身创建，但是Hibernate推荐通过Hibernate生成
    /* 指定主键的生成策略,
     * TABLE    使用表生成主键
     * SEQUENCE 使用数据序列，Oracle适用。
     *      @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySeq")
     *      @SequenceGenerator(name = "categorySeq", initialValue = 1, allocationSize = 1, sequenceName = "SEQ_CATEGORY")
     * IDENTITY 使用自增长主键，MySQL适用，表主键字段必须设置为自增长。
     * AUTO 根据数据库情况自动选择.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //声明该属性与数据库字段的映射关系
    private Integer id;

    private String name;

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
        this.name = name;
    }
}