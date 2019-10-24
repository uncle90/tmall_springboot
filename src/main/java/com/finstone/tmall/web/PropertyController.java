package com.finstone.tmall.web;

import com.finstone.tmall.dao.PropertyDao;
import com.finstone.tmall.pojo.Page4JPA;
import com.finstone.tmall.pojo.Property;
import com.finstone.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping(value="categories/{cid}/properties")
    public Page4JPA<Property> list(
            @PathVariable(name="cid") int cid,
            @RequestParam(name="start", defaultValue = "0") int start,
            @RequestParam(name="size", defaultValue = "" + Page4JPA.defaultSize) int size)
            throws Exception{
        return propertyService.list(cid, start, size);
    }

}
