package com.caozhihu.tmall.action;

import com.caozhihu.tmall.service.ProductImageService;
import com.caozhihu.tmall.util.ImageUtil;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import java.io.File;

public class ProductImageAction extends Action4Result {

    @Action("admin_productImage_list")
    public String list() {
        productSingleImages = productImageService.list("product", product, "type", ProductImageService.type_single);
        productDetailImages = productImageService.list("product", product, "type", ProductImageService.type_detail);
        t2p(product);
        return "listProductImage";
    }

    @Action("admin_productImage_add")
    public String add() {
        productImageService.save(productImage);

        String folder = "img/";
        if (ProductImageService.type_single.equals(productImage.getType())) {
            folder += "productSingle";
        } else {
            folder += "productDetail";
        }

        File imageFolder = new File(ServletActionContext.getServletContext().getRealPath(folder));
        File file = new File(imageFolder, productImage.getId() + ".jpg");
        String fileName = file.getName();

        saveWithJpg(file);//把临时img复制给file

        if (ProductImageService.type_single.equals(productImage.getType())) {
            String imageFolder_small = ServletActionContext.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = ServletActionContext.getServletContext().getRealPath("img/productSingle_middle");

            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);

            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();

            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }

        return "listProductImagePage";
    }

    @Action("admin_productImage_delete")
    public String delete() {

        //因为此时的对象只有productImage.id，待会删除成功是要跳转/admin_productImage_list?product.id=${productImage.product.id}
        //如果不先转换为持久对象，会报错Caused by: org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing
        t2p(productImage);
        propertyService.delete(productImage);
        return "listProductImagePage";
    }
}
