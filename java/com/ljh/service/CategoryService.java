package com.ljh.service;

import java.util.List;

public interface CategoryService {
    boolean insertCategory(String categoryName, int brandId);

    boolean deleteCategory(int brandId);

    List<String> selectCategoryName(int brandId);
 }
