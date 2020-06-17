package com.finstone.tmall.web;

import com.finstone.tmall.pojo.Product;
import com.finstone.tmall.pojo.ProductImage;
import com.finstone.tmall.service.ProductImageService;
import com.finstone.tmall.service.ProductService;
import com.finstone.tmall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductImageController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    /**
     * 查询指定产品pid的图片——区分类型：概况图、详情图
     * @param pid
     * @param type
     * @return
     */
    @GetMapping("products/{pid}/productimages")
    public List<ProductImage> list(@PathVariable(name="pid") int pid, @RequestParam(name="type") String type){
        if(ProductImageService.SINGLE.equals(type)){
            //单个（类）图片
            List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.SINGLE);
            return pisSingle;
        }else if(ProductImageService.DETAIL.equals(type)){
            //详情（类）图片
            List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.DETAIL);
            return pisDetail;
        }else{
            return new ArrayList<ProductImage>();
        }
    }

    @GetMapping("productimages/{id}")
    public ProductImage get(@PathVariable(name="id") int id) throws Exception{
        return productImageService.get(id);
    }

    @PostMapping("productimages")
    public ProductImage add(@RequestParam(name="pid") int pid,
                            @RequestParam(name="type") String type,
                            MultipartFile image,
                            HttpSession session) throws Exception{
        //添加记录
        Product product = productService.get(pid);
        ProductImage pi = new ProductImage();
        pi.setProduct(product);
        pi.setType(type);
        pi = productImageService.add(pi);

        //创建保存对象（目录）
        String fileName = pi.getId()+".jpg";
        String imageFolder;
        String imageFolder_middle = null;
        String imageFolder_small  = null;
        if(ProductImageService.SINGLE.equals(pi.getType())){
            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
            imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
        }else if(ProductImageService.DETAIL.equals(pi.getType())){
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
        }else{
            throw new Exception("图片类型错误，保存失败。");
        }
        File file = new File(imageFolder,fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        //保存文件
        image.transferTo(file);//接收上传文件
        BufferedImage img = ImageUtil.change2jpg(file);//转换为jpg格式
        ImageIO.write(img, "jpg", file);//保存为新格式
        if(ProductImageService.SINGLE.equals(pi.getType())){
            File file_middle = new File(imageFolder_middle,fileName);
            File file_smal = new File(imageFolder_small,fileName);
            if(!file_middle.getParentFile().exists()) file_middle.getParentFile().mkdirs();
            if(!file_smal.getParentFile().exists()) file_smal.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, new File(imageFolder_middle,fileName));
            ImageUtil.resizeImage(file, 217, 190, new File(imageFolder_small,fileName));
        }

        return pi;
    }

    @DeleteMapping("productimages/{id}")
    public Object delete(@PathVariable(name="id") int id, HttpSession session) throws Exception{
        //取记录
        ProductImage pi = productImageService.get(id);
        //删记录
        productImageService.delete(id);
        //删文件
        String fileName = id+".jpg";
        File file;
        File file_middle = null;
        File file_small  = null;
        if(ProductImageService.SINGLE.equals(pi.getType())){
            file = new File(session.getServletContext().getRealPath("img/productSingle"), fileName);
            file_middle = new File(session.getServletContext().getRealPath("img/productSingle_middle"), fileName);
            file_small  = new File(session.getServletContext().getRealPath("img/productSingle_small") , fileName);
            file.delete();
            file_middle.delete();
            file_small.delete();
        }else if(ProductImageService.DETAIL.equals(pi.getType())){
            file = new File(session.getServletContext().getRealPath("img/productDetail"), fileName);
            file.delete();
        }else{
            throw new Exception("图片类型错误，删除失败。");
        }
        return null;
    }

    @PutMapping("productimages/{id}")
    public ProductImage update(@PathVariable(name="id") int id) throws Exception{
        return null;
    }
}
