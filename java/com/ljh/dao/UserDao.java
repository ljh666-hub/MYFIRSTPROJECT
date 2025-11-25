package com.ljh.dao;

import com.ljh.pojo.Brands;
import com.ljh.pojo.Users;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserDao {
    @Select("select count(*) from Users")
    int countUsers();

    @Select("<script>select count(*) num ,convert(date,RegisterTime) registerTime from Users <where> <if test='registerTime1!=null and registerTime2 != null'> and RegisterTime &gt;= #{registerTime1} and RegisterTime &lt;= #{registerTime2} </if></where> group by convert(date,RegisterTime)</script>")
    List<Users> CountRegister(@Param("registerTime1") LocalDate registerTime1, @Param("registerTime2") LocalDate registerTime2);

    @Select("select  * from Users where UserName=#{userName} and UserPassword=#{userPassword}")
    Users selectUser(@Param("userName") String userName, @Param("userPassword") String userPassword);

    @Select("select  * from Users where UserName=#{userName}")
    Users selectByUserName(String userName);

    @Select("select * from Users where UserID = #{userId}")
    Users selectByUserId(int userId);

    @Select("select * from Users order by UserID asc offset (${nowPage}-1)*8 rows fetch next 8 rows only")
    List<Users> userPage(int nowPage);

    @Insert("insert into Users(UserName,UserPassword,UserAddress,UserTelephone,ImageUrl,UserStatus) " +
            "values(#{userName},#{userPassword},#{userAddress},#{userTelephone},#{imageUrl},'启用')")
    int insertUser(Users user);

    @Update("update Users set UserStatus = #{userStatus} where UserID=#{userId}")
    int updateUser(@Param("userStatus") String userStatus,@Param("userId") int userId);

    @Update("update Users set Unread = Unread+1 where UserId = #{userId}")
    int updateUnread(int userId);

    @Update("update Users set Unread = 0 where UserId = #{userId}")
    int clearUnread(int userId);

    @Update("<script> update Users <set> <if test='userTelephone != null and userTelephone != \"\"'> UserTelephone = #{userTelephone} </if>" +
            " <if test='userName != null and userName != \"\"'>, UserName = #{userName} </if> <if test='userPassword != null and userPassword != \"\"'> ,UserPassword = #{userPassword} </if> " +
            "<if test='userAddress != null and userAddress != \"\"'> ,UserAddress = #{userAddress} </if> <if test='userStatus != null and userStatus != \"\"'> UserStatus = #{userStatus} </if>" +
            "<if test='imageUrl != null and imageUrl != \"\"'> ,imageUrl = #{imageUrl} </if> </set> where UserID = #{userId} </script>")
    int updateUserDetail(Users user);
}
