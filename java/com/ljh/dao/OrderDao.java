package com.ljh.dao;

import com.ljh.pojo.Orders;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderDao {
    @Insert("insert into Orders(OrderID,OrderStatus,OrderAddress,UserID,BrandID,OrderRemark,Amount,OrderPrice,CategoryName)" +
    "values(#{orderId},'已付款',#{orderAddress},#{userId},#{brandId},#{orderRemark},#{amount},#{orderPrice},#{categoryName})")
    int insertOrder(Orders orders);

    @Select("select count(*) from Orders")
    int countOrders();

    @Select("<script> select convert(date,OrderTime) as OrderTime,Sum(OrderPrice) as OrderPrice from Orders <where> OrderStatus='已收货' <if test='orderTime1!=null and orderTime2 != null'> and convert(date,OrderTime) &gt;= #{orderTime1} and convert(date,OrderTime) &lt;= #{orderTime2} </if></where> group by convert(date,OrderTime) order by convert(date,OrderTime) </script>")
    List<Orders> CountTotal(@Param("orderTime1") LocalDate orderTime1, @Param("orderTime2") LocalDate orderTime2);

    @Select("<script> select * from Orders inner join Brands on Orders.BrandID = Brands.BrandID <where> UserID=#{userId} <if test='brandName != \"%null%\" and brandName != \"\"'> and BrandName like #{brandName} </if> <if test='orderStatus != null and orderStatus != \"\"'> and OrderStatus = #{orderStatus} </if> </where> order by OrderTime desc </script>")
    List<Orders> findByBrandId(@Param("userId") int userId,@Param("brandName") String brandName,@Param("orderStatus") String orderStatus);

    @Select("<script> select * from Orders "+"<where>"+"<if test='userId!=null and userId != 0'> and UserID=#{userId} </if>"+" <if test='orderStatus != null and orderStatus != \"\"'> and OrderStatus=#{orderStatus} " +
            "</if>"+" <if test='orderId != null and orderId != \"\"'> and OrderID=#{orderId} </if> "+"</where>"+" </script>")
    List<Orders> findByCondition(Orders orders);

    @Select("select sum(Orderprice) from Orders where convert(date,OrderTime) = #{orderTime}")
    Integer getSumPrice(LocalDate orderTime);

    @Select("<script> select * from Orders inner join Brands on Brands.BrandID = Orders.BrandID <where> <if test='orderTime1!=null and orderTime2 != null'> OrderTime &gt;= #{orderTime1} and OrderTime &lt;= #{orderTime2} </if> <if test='brandType != null and brandType != \"\"'> and BrandType = #{brandType} </if> <if test='orderStatus != null and orderStatus != \"\"'> and OrderStatus=#{orderStatus} </if> </where> order by OrderTime desc</script>")
    List<Orders> findByClient(@Param("orderTime1") LocalDateTime orderTime1, @Param("orderTime2") LocalDateTime orderTime2, @Param("brandType") String brandType,@Param("orderStatus") String orderStatus);

    @Select("select count(Distinct OrderID) from Orders where convert(date ,OrderTime)  <= #{time2} and convert(date ,OrderTime)  >= #{time1}")
    int findAmount(@Param("time1") LocalDate time1, @Param("time2") LocalDate time2);

    @Select("select * from Orders")
    List<Orders> findAll();

    @Select("<script> select count(distinct OrderID) as Amount,convert(date ,OrderTime) as OrderTime from Orders <where>orderStatus='已退款' or orderStatus='退款中'<if test='orderTime1!=null and orderTime2 != null'>and convert(date ,OrderTime) &gt;= #{orderTime1} and convert(date ,OrderTime) &lt;= #{orderTime2} </if></where> group by convert(date ,OrderTime) </script>")
    List<Orders> findCancel(@Param("orderTime1")  LocalDate orderTime1, @Param("orderTime2") LocalDate orderTime2);

    @Select("<script> select * from Orders inner join Brands on Orders.BrandID = Brands.BrandID  <where> <if test='orderId != null and orderId != \"\"'> Orders.OrderID = #{orderId} </if> <if test='brandName != \"%null%\" and brandName != \"\"'> and BrandName like #{brandName} </if> </where> </script>")
    List<Orders> findByOrderId(@Param("orderId") String orderId,@Param("brandName") String brandName);

    @Select("select * from Orders order by OrderTime desc offset (${nowPage}-1)*7 rows fetch next 7 rows only")
    List<Orders> orderPage(int nowPage);

    @Update("<script> update Orders <set> <if test='orderAddress != null and orderAddress != \"\"'>OrderAddress=#{orderAddress}</if><if test='orderRemark != null and orderRemark != \"\"'>,OrderRemark=#{orderRemark} </if>" +
            "<if test='orderStatus != null and orderStatus != \"\"'>,OrderStatus=#{orderStatus} </if> where OrderID=#{orderId} </set> </script>")
    int updateOrder(Orders orders);

    @Update("update Orders set OrderStatus=#{orderStatus} where OrderStatus='已签收' and OrderTime<#{time}")
    int updateOrderStatus(@Param("orderStatus") String orderStatus, @Param("time") LocalDateTime time);

    @Delete("delete from Orders where OrderID = #{orderId}")
    int deleteByOrderId(String orderId);
}
