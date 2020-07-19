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

    @Autowired
    PropertyValueService propertyValueService;

    public List<Property> list(){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Property> pts = propertyDao.findAll(sort);
        return pts;
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
        Page4JPA<Property> pts = new Page4JPA<Property>(page);
        return pts;
    }

    public Property add(Property property){
        return propertyDao.save(property);
    }

    /**
     * 删除指定属性，及所有相关产品的属性值
     * @param id 属性编号
     */
    public void delete(Integer id){
        //删除所有属性值（外键）
        Property property = propertyDao.findOne(id);
        propertyValueService.delete(property);
        //删除属性
        propertyDao.delete(id);
    }

    public Property get(Integer id){
        return propertyDao.findOne(id);
    }

    public Property update(Property property){
        return propertyDao.save(property);
    }

    /**
     * 查询指定分类下的所有属性（名）
     * @param cid
     * @return
     */
    public List<Property> list(Integer cid){
        Category category = categoryService.get(cid);
        List<Property> pts = propertyDao.findByCategory(category);
        return pts;
    }
}
