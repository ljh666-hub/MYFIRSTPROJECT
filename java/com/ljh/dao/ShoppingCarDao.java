package com.ljh.dao;

import com.ljh.pojo.ShoppingCar;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCarDao {
@Insert("insert into ShoppingCar(BrandID,Amount,Price,UserID,CategoryName) values(#{brandId},#{amount},#{price},#{userId},#{categoryName})")
int insert(ShoppingCar shoppingCar);

@Select("select count(*) from ShoppingCar")
int count();

@Select("select top 1 * from ShoppingCar inner join Brands on Shoppingcar.BrandID = Brands.BrandID where UserID = #{userId} order by CarID")
List<ShoppingCar> findFirst(int userId);

@Select("select * from ShoppingCar where UserID = #{userId} order by CarID offset (#{nowPage}-1)*6 rows fetch next 6 rows only ")
List<ShoppingCar> selectByUserId(@Param("userId") int userId,@Param("nowPage") int nowPage);

@Delete("delete from ShoppingCar where CarID = #{carId}")
int delete(int carId);

@Select("<script> select * from ShoppingCar inner join Brands on Shoppingcar.BrandID = Brands.BrandID <where> <if test='brandName != null and brandName != \"\"'>BrandName like #{brandName} </if> </where> </script>")
    List<ShoppingCar> selectByBrandName(String brandName);
}
