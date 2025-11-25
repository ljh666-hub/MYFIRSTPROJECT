package com.ljh.service.Impl;

import com.ljh.dao.CategoryDao;
import com.ljh.service.CategoryService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public boolean insertCategory(String categoryName, int brandId) {
        return categoryDao.insertCategory(categoryName,brandId)>0;
    }

    @Override
    public boolean deleteCategory(int brandId) {
        return categoryDao.deleteCategory(brandId)>0;
    }

    @Override
    public List<String> selectCategoryName(int brandId) {
        return categoryDao.selectCategoryName(brandId);
    }
}
