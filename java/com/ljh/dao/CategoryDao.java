package com.ljh.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryDao {
    @Select("select CategoryName from Category where BrandID=#{brandId}")
    List<String> selectCategoryName(int brandId);

    @Insert("insert into Category(CategoryName,BrandID) values(#{categoryName},#{brandId})")
    int insertCategory(String categoryName, int brandId);

    @Delete("delete from Category where BrandID=#{brandId}")
    int deleteCategory(int brandId);
}
