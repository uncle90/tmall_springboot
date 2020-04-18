package com.finstone.tmall.service;

import com.finstone.tmall.dao.CategoryDao;
import com.finstone.tmall.pojo.Category;
import com.finstone.tmall.pojo.Page4JPA;
import com.finstone.tmall.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public List<Category> list(){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryDao.findAll(sort);
    }

    /**
     * Spring JPA 分页查询
     * @param start 从0开始的页面索引
     * @param size 分页页面大小
     * @return
     */
    public Page4JPA<Category> list(int start, int size){
        //排序参数
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        //分页参数
        Pageable pageable = new PageRequest(start, size, sort);
        //Spring JPA分页查询
        Page page = categoryDao.findAll(pageable);
        //包装为自定义分页 bean 返回
        Page4JPA<Category> cs = new Page4JPA<Category>(page);
        return cs;
    }

    /**
     * 添加一条记录
     */
    public Category add(Category category){
        return categoryDao.save(category);
    }

    /**
     * 删除一条记录
     */
    public void delete(int id){
        categoryDao.delete(id);
    }

    /**
     * 查询一条记录，如果不存在放回null
     * 注：与findOne不同，getOne返回的是reference（代理对象），所以当没有满足条件的记录时会报错。
     * findOne（主键值）：底层调用的是em.find()        :立即加载
     * getOne(主键值) ： 底层调用的是em.getReference() : 延迟加载
     */
    public Category get(int id){
        return categoryDao.findOne(id);
    }

    /**
     * 修改一条记录
     * 插入 vs 修改。JPA会先判断是否为新记录，是则merge（修改），否则persist（插入）。
     */
    public Category update(Category category){
        return categoryDao.save(category);
    }

    /**
     * 去除Category.product中的Category属性，避免在JSON序列化时无限递归
     */
    public void removeCategoryFromProduct(List<Category> cs){
        for(Category category: cs){
            removeCategoryFromProduct(category);
        }
    }

    public void removeCategoryFromProduct(Category category){
        /**
         * Category.products
         * Category.productsByRow
         */
        List<Product> ps = category.getProducts();
        List<List<Product>> productsByRow = category.getProductsByRow();

        if(ps!=null){
            for(Product product: ps){
                product.setCategory(null);
            }
        }
        if(productsByRow!=null){
            for(List<Product> ps1: productsByRow){
                for(Product product1: ps1){
                    product1.setCategory(null);
                }
            }
        }

    }

}
