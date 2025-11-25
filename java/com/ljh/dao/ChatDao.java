package com.ljh.dao;

import com.ljh.pojo.Chat;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatDao {
@Insert("insert into Chat(SendID,ReceiveID,Content,SendTime) values(#{sendId},#{receiveId},#{content},#{sendTime})")
    int addChat(Chat chat);

@Select("select * from Chat where SendID != 0" )
List<Chat> getUser();

    @Select("select Top 1 * from Chat where (SendID=#{sendId} and ReceiveID=0) or (SendID=0 and ReceiveID=#{sendId}) order by SendTime desc" )
    Chat Recent(int sendId);

@Select("select * from Chat where (SendID=#{sendId} and ReceiveID=0) or (SendID=0 and ReceiveID=#{sendId}) order by SendTime asc")
    List<Chat> getChat(int sendId);
}
