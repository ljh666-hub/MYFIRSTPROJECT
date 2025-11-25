package com.ljh.dao;

import com.ljh.DTO.BrandSelectDTO;
import com.ljh.pojo.Brands;
import com.ljh.pojo.Orders;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BrandDao {
    @Select("select * from Brands")
    List<Brands> findAllBrands();

    @Select("select count(*) from Brands where BrandAmount > 0")
    Integer findSelling();

    @Select("select count(*) from Brands where BrandAmount = 0")
    Integer findUnSelling();

    @Select("select * from Brands where BrandID = #{brandId}")
    Brands findBrandById(int brandId);

    @Select("select Brands.* from Orders inner join Users on Users.UserID=Orders.UserID inner join Brands on Orders.BrandID=Brands.BrandID where UserName=#{userName}")
    List<Brands> findAllBrandsByUserName(String userName);

    @Select("<script>"+"select * from Brands" + "<where>"+"<if test='brandName != null and brandName != \"\"'>"+"BrandName like #{brandName} " +
             "</if>"+"<if test='brandType != null and brandType != \"\"'>"+"and BrandType=#{brandType}"+"</if>"+"and BrandPrice &gt;= #{brandPriceBegin} and BrandPrice &lt;= #{brandPriceEnd} </where> " +
            "order by BrandSell<if test='rate != null and rate != \"\"'>  ${rate} </if> offset (${nowPage}-1)*8 rows fetch next 8 rows only"+"</script>")
    List<Brands> findBrandsByCondition(@Param("brandName") String brandName,@Param("brandType") String brandType,@Param("brandPriceBegin") int brandPriceBegin,@Param("brandPriceEnd") int brandPriceEnd,@Param("rate")  String rate,@Param("nowPage") int nowPage);

    @Select("<script>"+"select count(*) from Brands" + "<where>"+"<if test='brandName != null and brandName != \"\"'>"+"BrandName like #{brandName} " +
            "</if>"+"<if test='brandType != null and brandType != \"\"'>"+"and BrandType=#{brandType}"+"</if>"+" </where> " +
            " </script>")
    int findAmountByCondition(@Param("brandName") String brandName,@Param("brandType") String brandType);


    @Select("<script> select distinct top 5 BrandName, BrandSell from Brands inner join Orders on Orders.BrandID = Brands.BrandID<where> OrderStatus='已收货' <if test='orderTime1!=null and orderTime2 != null'> and OrderTime &gt;= #{orderTime1} and OrderTime &lt;= #{orderTime2} </if> </where>order by BrandSell desc</script> ")
    List<Orders> SellsTop(@Param("orderTime1") LocalDate orderTime1, @Param("orderTime2") LocalDate orderTime2);

    @Select("select count(*) from Brands")
    int countBrands();

    @Select("select * from Brands order by BrandID asc offset (${nowPage}-1)*8 rows fetch next 8 rows only")
    List<Brands> brandPage(int nowPage);

    @Insert("insert into Brands(BrandName,BrandAmount,BrandPrice,BrandType,BrandDetail,BrandSell,ImageUrl,DetailImageUrl) " +
            "values(#{brandName},#{brandAmount},#{brandPrice},#{brandType},#{brandDetail},#{brandSell},#{imageUrl},#{detailImageUrl})")
    @Options(useGeneratedKeys = true,keyProperty = "brandId",keyColumn = "BrandID")
    Integer insertBrand(Brands brand);

    @Delete("delete from Brands where BrandID = #{brandId}")
    Integer deleteBrand(int brandId);

    @Update("<script>"+"update Brands" + "<set>"+" BrandName = #{brandName},BrandAmount=#{brandAmount},BrandPrice=#{brandPrice}," +
            "BrandDetail=#{brandDetail},BrandSell= #{brandSell}," +"<if test='imageUrl != null and imageUrl != \"\"'>"+
            "ImageUrl = #{imageUrl},"+"</if>"+"<if test='detailImageUrl != null and detailImageUrl != \"\"'> DetailImageUrl=#{detailImageUrl} </if>"+"</set>"+"where BrandID = #{brandId}"+"</script>")
    Integer updateBrand(Brands brand);

    @Update("update Brands set BrandAmount =#{amount},BrandSell=#{sell} from Brands inner join Orders on Brands.brandId=Orders.brandId  where Brands.BrandID=#{brandId}")
    Integer updateBrandByBrandId(@Param("amount") int amount,@Param("sell") int sell,@Param("brandId")  int brandId);

    @Update("update Brands set BrandAmount =#{amount},BrandSell=#{sell} from Brands inner join Orders on Brands.brandId = Orders.brandId where Brands.BrandID=#{brandId}")
    Integer updateBrandRefund(@Param("amount") int amount,@Param("sell") int sell,@Param("brandId")  int brandId);
}
