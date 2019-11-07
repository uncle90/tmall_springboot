package com.finstone.tmall.service;

import com.finstone.tmall.dao.ProductDao;
import com.finstone.tmall.pojo.Category;
import com.finstone.tmall.pojo.Page4JPA;
import com.finstone.tmall.pojo.Product;
import com.finstone.tmall.pojo.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    CategoryService categoryService;

    public Product get(Integer id){
        return productDao.findOne(id);
    }

    public List<Product> list(){
        return productDao.findAll();
    }

    /**
     * Spring JPA 分页查询指定分类下的所有产品
     * @param cid 分类编号
     * @param start 从0开始的页面索引
     * @param size 分页页面大小
     * @return
     */
    public Page4JPA<Product> list(Integer cid, Integer start, Integer size){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Category category = categoryService.get(cid);
        Page<Product> page = productDao.findByCategory(category, pageable);
        Page4JPA<Product> ps = new Page4JPA<>(page);
        return ps;
    }

    public Product add(Product product){
        return productDao.save(product);
    }

    public void delete(Integer id){
        productDao.delete(id);
    }

    public Product update(Product product){
        return productDao.save(product);
    }

}
