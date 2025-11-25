package com.ljh.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminPictureDao {
    @Insert("insert into AdminPicture(imageUrl) values(#{imageUrl})")
    int insert(String imageUrl);

    @Delete("delete from AdminPicture")
    int deleteAll();

    @Select("select imageUrl from AdminPicture")
    List<String> selectAll();
}
