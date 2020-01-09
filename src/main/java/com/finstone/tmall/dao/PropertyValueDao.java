package com.finstone.tmall.dao;

import com.finstone.tmall.pojo.Product;
import com.finstone.tmall.pojo.Property;
import com.finstone.tmall.pojo.PropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyValueDao extends JpaRepository<PropertyValue, Integer>{

    //根据产品id和属性名查询属性值
    PropertyValue findByProductAndProperty(Product product, Property property);

    List<PropertyValue> findByProduct(Product product);

    List<PropertyValue> findByProperty(Property property);

    void deleteByProperty(Property property);
}