package com.finstone.tmall.web;

import com.finstone.tmall.pojo.Page4JPA;
import com.finstone.tmall.pojo.Product;
import com.finstone.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    ProductService ProductService;

    @GetMapping("categories/{cid}/products")
    public Page4JPA<Product> list(
            @PathVariable(name="cid") int cid,
            @RequestParam(name="start", defaultValue = "0") int start,
            @RequestParam(name="size", defaultValue = "" + Page4JPA.defaultSize) int size)
            throws Exception{
        //return ProductService.list(cid, start, size);
        Page4JPA<Product> ps = ProductService.list(cid, start, size);
        return ps;
    }

    @GetMapping("products/{id}")
    public Product get(@PathVariable(name="id") int id)throws Exception{
        return ProductService.get(id);
    }

    @PostMapping("products")
    public Object add(@RequestBody Product Product)throws Exception{
        return ProductService.add(Product);
    }

    @DeleteMapping("products/{id}")
    public Object delete(@PathVariable(name="id") int id)throws Exception{
        ProductService.delete(id);
        return null;
    }

    @PutMapping("products/{id}")
    public Product update(
            @PathVariable(name="id") int id,
            @RequestBody Product Product)throws Exception{
        return ProductService.update(Product);
    }

}
