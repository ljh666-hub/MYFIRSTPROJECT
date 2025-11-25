package com.ljh.controller;

import com.ljh.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/addCategory")
    @ResponseBody
    public void addCategory(@RequestBody Map<String, Object> categoryList) {
        int brandId = (int) categoryList.get("brandId");
        List<Map<String,String>> param= (List<Map<String, String>>) categoryList.get("categoryName");
        for (Map<String, String> map : param) {
            String categoryName = map.get("value");
            categoryService.insertCategory(categoryName, brandId);
        }
    }

    @RequestMapping("/selectCategory")
    @ResponseBody
    public List<String> selectCategory(int brandId) {
        return categoryService.selectCategoryName(brandId);
    }

    @RequestMapping("/deleteCategory")
    @ResponseBody
    public void deleteCategory(int brandId) {
        categoryService.deleteCategory(brandId);
    }
}