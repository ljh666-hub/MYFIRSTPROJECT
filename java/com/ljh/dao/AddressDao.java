package com.ljh.dao;

import com.ljh.pojo.Address;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressDao {
    @Select("select * from Addresses where UserID = #{userId}")
    List<Address> findByUserId(int userId);

    @Insert("insert into Addresses(AddressDetail,RealName,UserID,AddressTag) values(#{addressDetail},#{realName},#{userId},#{addressTag})")
    int addAddress(Address address);

    @Delete("delete from Addresses where AddressID =#{addressId}")
    int deleteAddress(int addressId);
}
