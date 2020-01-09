package com.finstone.tmall.web;

import com.finstone.tmall.pojo.Product;
import com.finstone.tmall.pojo.PropertyValue;
import com.finstone.tmall.service.ProductService;
import com.finstone.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PropertyValueController {

    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    ProductService productService;

    /**
     * 初始化产品属性，查看产品属性list
     * @param pid
     * @return
     */
    @GetMapping("products/{ptid}/propertyvalues")
    public List<PropertyValue> list(@PathVariable(name="ptid") int pid){
        //初始化产品属性
        Product product = productService.get(pid);
        propertyValueService.init(product);
        //查看产品属性list
        List<PropertyValue> pvs = propertyValueService.list(pid);
        return pvs;
    }

    @PutMapping("propertyvalues")
    public PropertyValue update(@RequestBody PropertyValue bean) throws Exception{
        PropertyValue pv = propertyValueService.update(bean);
        return pv;
    }


}
