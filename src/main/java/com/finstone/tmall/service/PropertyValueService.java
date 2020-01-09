package com.finstone.tmall.service;

import com.finstone.tmall.dao.PropertyValueDao;
import com.finstone.tmall.pojo.Product;
import com.finstone.tmall.pojo.Property;
import com.finstone.tmall.pojo.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueService {

    @Autowired
    PropertyValueDao propertyValueDao;

    @Autowired
    ProductService productService;

    @Autowired
    PropertyService propertyService;

    /**
     * 根据ID查询指定属性值
     * @param id
     * @return
     */
    public PropertyValue get(Integer id){
        return propertyValueDao.findOne(id);
    }

    /**
     * 属性值管理——查询指定产品（产品编号）的所有属性键值对（属性名，属性值）。
     * @param pid
     * @return
     */
    public List<PropertyValue> list(Integer pid){
        Product product = productService.get(pid);
        List<PropertyValue> pvs = propertyValueDao.findByProduct(product);
        return pvs;
    }

    /**
     * 属性值管理——修改指定产品的属性值
     * @param pv
     * @return
     */
    public PropertyValue update(PropertyValue pv){
        propertyValueDao.save(pv);
        return pv;
    }

    /**
     * 按产品所属类别，初始化（新增）每种产品的属性名称（属性值为空）。
     * pid ==> category ==> property ==> propertyvalue
     * @param product
     */
    public void init(Product product){
        //查所属分类
        List<Property> pts = propertyService.list(product.getCategory().getId());

        //按产品、属性名遍历所有属性值，如果不存在，则新增 <属性名,空属性值>。
        //insert into propertyvalue (pid, ptid, value) values (?, ?, ?)
        for(Property property: pts){
            PropertyValue pv = propertyValueDao.findByProductAndProperty(product, property);
            if(pv == null){
                pv = new PropertyValue();
                pv.setProduct(product);
                pv.setProperty(property);
                propertyValueDao.save(pv);
            }
        }


    }

    public void delete(Property property){
        propertyValueDao.deleteByProperty(property);
    }

}
