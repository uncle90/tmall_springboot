package com.finstone.tmall.dao;

import com.finstone.tmall.pojo.Category;
import com.finstone.tmall.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product,Integer> {

    Page<Product> findByCategory(Category category, Pageable pageable);

}
