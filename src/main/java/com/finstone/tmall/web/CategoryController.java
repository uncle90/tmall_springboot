package com.finstone.tmall.web;

import com.finstone.tmall.pojo.Category;
import com.finstone.tmall.pojo.Page4JPA;
import com.finstone.tmall.service.CategoryService;
import com.finstone.tmall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /*@GetMapping(value="/categories")
    public List<Category> list() throws Exception{
        return categoryService.list();
    }*/

    /**
     * 查询分类
     */
    @GetMapping(value="/categories")
    public Page4JPA<Category> list(
            @RequestParam(name="start", defaultValue = "0") int start, //name和value参数等价，互为别名
            @RequestParam(name="size", defaultValue = "" + Page4JPA.defaultSize) int size)
            throws Exception {
        return categoryService.list(start, size);
    }

    /**
     * 新增分类
     */
    @PostMapping("/categories")
    public Object add(Category category, HttpSession session, MultipartFile image) throws IOException {
        //新增分类
        category = categoryService.add(category);
        //保存图片
        saveUploadImage(category, session, image);
        return category;
    }

    /**
     * 保存上传图片，异常上抛
     */
    public void saveUploadImage(Category category, HttpSession session, MultipartFile image) throws IOException {
        //HttpServletRequest request.getSession().getServletContext().getRealPath("/")
        //HttpServletRequest request.getServletContext().getRealPath("/")
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId()+".jpg"); //创建图片对象
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        image.transferTo(file);   //接收上传图片
        BufferedImage img = ImageUtil.change2jpg(file);//转换图片（数据存储）格式为jpg
        ImageIO.write(img, "jpg", file); //保存图片到文件
    }

    /**
     * 删除保存的图片
     */
    public void deleteArchivedImage(int id, HttpSession session) throws IOException {
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id+".jpg");
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 删除分类
     * {@link PathVariable}用来绑定URI模板变量.
     */
    @DeleteMapping("/categories/{id}")
    //@RequestMapping(method = RequestMethod.DELETE, value="/categories/{id}")
    public String delete(@PathVariable("id") int id, HttpSession session) throws IOException {
        categoryService.delete(id);
        deleteArchivedImage(id, session);
        return null;
    }

    /**
     * 查询一个分类
     */
    @GetMapping(value="/categories/{id}")
    public Category get(@PathVariable("id") int id) throws Exception {
        return categoryService.get(id);
    }

    /**
     * 修改一个分类
     * POST用来“增资源”，重复请求时，后一个请求不会覆盖前一个请求的结果；
     * PUT 用来“改资源”，重复请求时，后一个请求  会覆盖前一个请求的结果；
     * 注：PUT请求需要
     */
    @PutMapping(value="/categories")
    public Object update(@RequestBody Category category, HttpSession session, MultipartFile image) throws Exception {
        if(category.getId() == null){
            return "111111";
        }
        return categoryService.update(category);
    }

}
