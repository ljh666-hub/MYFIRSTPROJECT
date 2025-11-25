package com.ljh.dao;

import com.ljh.pojo.Cupon;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CuponDao {
    @Insert("insert into cupon(CuponType,DiscountValue,MoneyLimit,TypeLimit,UserLimit,EndTime,CuponStatus,CuponAmount) values " +
            "(#{cuponType},#{discountValue},#{moneyLimit},#{typeLimit},#{userLimit},#{endTime},'有效',#{cuponAmount})")
    int insert(Cupon cupon);

    @Insert("insert into CuponUser(CuponID,UserID,UseStatus) values(#{cuponId},#{userId},'待使用')")
    int insertCuponUser(Cupon cupon);

    @Select("select * from Cupon")
    List<Cupon> selectAllCupon();

    @Select("<script> select * from cupon  inner join CuponUser on cupon.CuponID = CuponUser.CuponID inner join Users on CuponUser.UserID = Users.UserID  <where> CuponStatus = '有效' and CuponUser.UserID = #{userId}<if test='typeLimit != null and typeLimit != \"\" '>and TypeLimit=#{typeLimit} or TypeLimit='不限'</if> " +
            "<if test='moneyLimit != 0 and moneyLimit != null '>and MoneyLimit &lt; #{moneyLimit} </if> <if test='userLimit != null '>and UserLimit &gt; #{registerTime} </if> </where></script>")
    List<Cupon> selectUseful(Cupon cupon);

    @Select("<script> select * from cupon  <where> CuponStatus = '有效' <if test='typeLimit != null and typeLimit != \"\" '> TypeLimit=#{typeLimit} or TypeLimit='不限'</if> " +
            "<if test='moneyLimit != 0 and moneyLimit != null '>and MoneyLimit &lt; #{moneyLimit} </if> <if test='userLimit != null '>and UserLimit &gt; #{registerTime} </if></where>   </script>")
    List<Cupon> selectByCondition(Cupon cupon);

    @Select("<script> select * from cupon inner join CuponUser on cupon.CuponID = CuponUser.CuponID inner join Users on CuponUser.UserID = Users.UserID <where> Users.UserID=#{userId} <if test='cuponId != 0 and cuponId != null '> and Cupon.CuponID = #{cuponId} </if> and UseStatus = '待使用'</where> </script>")
    List<Cupon> selectNewUser(@Param("userId") int userId,@Param("cuponId") int cuponId);

    @Select("<script> select count(*) from cupon inner join CuponUser on cupon.CuponID = CuponUser.CuponID inner join Users on CuponUser.UserID = Users.UserID <where> Users.UserID=#{userId} <if test='cuponId != 0 and cuponId != null '> and Cupon.CuponID = #{cuponId} </if></where> </script>")
    int isNewUser(@Param("userId") int userId,@Param("cuponId") int cuponId);

    @Select("<script>select * from cupon <where> <if test='cuponStatus!=null and cuponStatus != \"\"'> CuponStatus =#{cuponStatus} </if> </where></script>")
    List<Cupon> selectByCuponStatus(String cuponStatus);

    @Update("update cupon set CuponAmount = (CuponAmount-1) where CuponID = #{cuponId}")
    int updateCuponAmount(int cuponId);

    @Update("update cupon set EndTime = #{endTime} where CuponID = #{cuponId}")
    int updateCuponEndTime(Cupon cupon);

    @Update("update cupon set CuponStatus = '已过期' where EndTime < #{now}")
    int updateByEndTime(LocalDate now);

    @Update("update CuponUser set UseStatus = '已使用',OrderId = #{orderId} where UserID = #{userId} and CuponID = #{cuponId}")
    int updateCuponUse(@Param("orderId")  String orderId,@Param("userId") int userId,@Param("cuponId") int cuponId);

    @Update("update cupon set CuponStatus = '有效' where CuponID = #{cuponId}")
    int updateByCuponId(int cuponId);

    @Delete("delete from cupon where CuponID = #{cuponId}")
    int deleteCupon(int cuponId);
}
