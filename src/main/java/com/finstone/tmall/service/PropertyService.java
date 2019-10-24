package com.finstone.tmall.service;

import com.finstone.tmall.dao.PropertyDao;
import com.finstone.tmall.pojo.Category;
import com.finstone.tmall.pojo.Page4JPA;
import com.finstone.tmall.pojo.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    @Autowired
    PropertyDao propertyDao;

    @Autowired
    CategoryService categoryService;

    public List<Property> list(){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Property> ps = propertyDao.findAll(sort);
        return ps;
    }

    /**
     * Spring JPA 分页查询
     * @param cid 分类编号
     * @param start 从0开始的页面索引
     * @param size 分页页面大小
     * @return
     */
    public Page4JPA<Property> list(int cid, int start, int size){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        //Page page = propertyDao.findAll(pageable);
        Category category = categoryService.get(cid);
        Page page = propertyDao.findByCategory(category, pageable);
        Page4JPA<Property> ps = new Page4JPA<Property>(page);
        return ps;
    }

}
