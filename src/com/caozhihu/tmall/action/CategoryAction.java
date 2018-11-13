package com.caozhihu.tmall.action;

import com.caozhihu.tmall.util.ImageUtil;
import com.caozhihu.tmall.util.Page;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CategoryAction extends Action4Result{

    @Action("admin_category_list")
    public String list() {
        if (page == null) {
            page = new Page();
        }
        int total = categoryService.total();
        page.setTotal(total);

        categories = categoryService.listByPage(page);
        return "listCategory";
    }

    @Action("admin_category_add")
    public String add() {
        categoryService.save(category);
        File imageFolder = new File(ServletActionContext.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");
        saveWithJpg(file);
        return "listCategoryPage";
    }

    @Action("admin_category_update")
    public String update() {
        categoryService.update(category);
        if (img != null) {
            File imageFolder = new File(ServletActionContext.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, category.getId() + ".jpg");
            saveWithJpg(file);
        }
        return "listCategoryPage";
    }



    @Action("admin_category_delete")
    public String delete() {
        categoryService.delete(category);
        return "listCategoryPage";
    }

    @Action("admin_category_edit")
    public String edit() {
        t2p(category);//此处来自url,admin_category_edit?category.id=27
        return "editCategory";
    }

}
