package com.finstone.tmall.dao;

import com.finstone.tmall.pojo.Category;
import com.finstone.tmall.pojo.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyDao extends JpaRepository<Property, Integer> {

    //多对一查询
    Page findByCategory(Category category, Pageable pageable);

    List<Property> findByCategory(Category category);
}
