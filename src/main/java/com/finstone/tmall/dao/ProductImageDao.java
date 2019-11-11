package com.finstone.tmall.dao;

import com.finstone.tmall.pojo.Category;
import com.finstone.tmall.pojo.Product;
import com.finstone.tmall.pojo.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageDao extends JpaRepository<ProductImage,Integer> {

    List<ProductImage> findByProduct(Product product, Sort sort);

    List<ProductImage> findByProductAndTypeEquals(Product product, String type, Sort sort);
    
}
