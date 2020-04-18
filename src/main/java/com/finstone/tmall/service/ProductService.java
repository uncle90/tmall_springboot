package com.finstone.tmall.service;

import com.finstone.tmall.dao.ProductDao;
import com.finstone.tmall.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductImageService productImageService;

    public Product get(Integer id){
        return productDao.findOne(id);
    }

    public List<Product> list(){
        return productDao.findAll();
    }

    /**
     * 查询某个分类下的所有产品
     * @return
     */
    public List<Product> list(Category category){
        List<Product> ps = productDao.findByCategory(category);
        return ps;
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
        //添加商品封面图片
        List<Product> products = ps.getContent();
        this.setFirstProductImage(products);
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

    /**
     * 为某个分类填充产品集合
     * @param category
     */
    public void fillProducts(Category category){
        List<Product> ps = this.list(category);
        this.setFirstProductImage(ps);//添加商品封面图片
        category.setProducts(ps);
    }

    /**
     * 为多个分类填充产品集合
     * @param cs
     */
    public void fillProducts(List<Category> cs){
        for(Category category: cs){
            fillProducts(category);
        }
    }

    /**
     * 为多个分类填充推荐产品集合，8个一行。
     * @param cs
     */
    public void fillProductsByRow(List<Category> cs){
        int productNumberEachRow = 8;
        for(Category category: cs){
            List<Product> ps = this.list(category);
            List<List<Product>> productsByRow = new ArrayList<>();
            for(int i = 0; i < ps.size(); i+=productNumberEachRow){
                int end = i + productNumberEachRow>ps.size()?
                        ps.size():i+productNumberEachRow;
                List<Product> productsOfRow = ps.subList(i,end);
                productsByRow.add(productsOfRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    //设置产品的封面图片，取id最大的type_single类图片
    void setFirstProductImage(Product product){
        List<ProductImage> pis = productImageService.list(product.getId(), ProductImageService.SINGLE);
        if(null != pis && !pis.isEmpty()){
            ProductImage pi = pis.get(0);
            product.setFirstProductImage(pi);
        }
    }

    void setFirstProductImage(List<Product> ps){
        for(Product product: ps){
            this.setFirstProductImage(product);
        }
    }

}
