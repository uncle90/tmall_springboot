package com.finstone.tmall.web;

import com.finstone.tmall.dao.PropertyDao;
import com.finstone.tmall.pojo.Category;
import com.finstone.tmall.pojo.Page4JPA;
import com.finstone.tmall.pojo.Property;
import com.finstone.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping("categories/{cid}/properties")
    public Page4JPA<Property> list(
            @PathVariable(name="cid") int cid,
            @RequestParam(name="start", defaultValue = "0") int start,
            @RequestParam(name="size", defaultValue = "" + Page4JPA.defaultSize) int size)
            throws Exception{
        return propertyService.list(cid, start, size);
    }

    @GetMapping("properties/{id}")
    public Property get(@PathVariable(name="id") int id)throws Exception{
        return propertyService.get(id);
    }

    @PostMapping("properties")
    public Object add(@RequestBody Property property)throws Exception{
        return propertyService.add(property);
    }

    @DeleteMapping("properties/{id}")
    public Object delete(@PathVariable(name="id") int id)throws Exception{
        propertyService.delete(id);
        return null;
    }

    @PutMapping("properties/{id}")
    public Property update(
            @PathVariable(name="id") int id,
            @RequestBody Property property)throws Exception{
        return propertyService.update(property);
    }

}
