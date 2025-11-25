package com.ljh.dao;

import com.ljh.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDao {
    @Insert("insert into Comments(CommentDetail,BrandID,UserID,Rate,RateName) values(#{commentDetail},#{brandId},#{userId},#{rate},#{rateName})")
    int insertComment(Comment comment);

    @Select("select * from Comments where CommentID=#{commentId}")
    List<Comment> selectComment(int commentId);

    @Select("select * from Comments inner join Brands on Comments.BrandID = Brands.BrandID where UserID = #{userId} order by CommentTime desc")
    List<Comment> selectCommentByUserId(int userId);

    @Select("<script> select count(*) from Comments <where> <if test='brandId != null and brandId != 0'> BrandID = #{brandId} </if> </where> </script>")
    int selectCommentCount(int brandId);

    @Select("select * from Comments order by CommentID desc offset (#{nowPage}-1)*8 rows fetch next 8 rows only")
    List<Comment> selectAllComment(int nowPage);

    @Select("<script> select * from Comments inner join Users on Comments.userId=Users.userId <where> <if test='rateName != null and rateName !=  \"\" '> RateName=#{rateName} </if> and BrandID=#{brandId} </where> </script>")
    List<Comment> selectAll(@Param("brandId") int brandId,@Param("rateName") String rateName);

    @Delete("delete from Comments where UserID = #{userId}")
    int deleteComment(int userId);

    @Delete("delete from Comments where CommentId = ${commentId}")
    int deleteCommentByCommentId(int commentId);


}
