package com.finstone.tmall.service;

import com.finstone.tmall.dao.ProductImageDao;
import com.finstone.tmall.pojo.Category;
import com.finstone.tmall.pojo.Page4JPA;
import com.finstone.tmall.pojo.Product;
import com.finstone.tmall.pojo.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {

    public static String SINGLE = "single";
    public static String DETAIL = "detail";

    @Autowired
    ProductImageDao productImageDao;

    @Autowired
    ProductService productService;

    public ProductImage add(ProductImage ProductImage){
        return productImageDao.save(ProductImage);
    }

    public void delete(Integer id){
        productImageDao.delete(id);
    }

    public ProductImage update(ProductImage productImage){
        return productImageDao.save(productImage);
    }

    public ProductImage get(Integer id){
        return productImageDao.findOne(id);
    }

    public List<ProductImage> list(Integer pid, String type){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Product product = productService.get(pid);
        return productImageDao.findByProductAndTypeEquals(product, type, sort);
    }



}
