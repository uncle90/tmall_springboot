package com.finstone.tmall.service;

import com.finstone.tmall.dao.CategoryDao;
import com.finstone.tmall.pojo.Category;
import com.finstone.tmall.pojo.Page4JPA;
import org.elasticsearch.common.recycler.Recycler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
}
